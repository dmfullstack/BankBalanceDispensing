package com.sa.discovery.dispensing.service;

import org.springframework.stereotype.Service;

import com.sa.discovery.dispensing.entity.*;
import com.sa.discovery.dispensing.exception.CustomException;
import com.sa.discovery.dispensing.model.*;
import com.sa.discovery.dispensing.repository.AtmAllocationRepository;
import com.sa.discovery.dispensing.repository.ClientAccountRepository;
import com.sa.discovery.dispensing.repository.ClientRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sa.discovery.dispensing.model.CurrencyAccountModel.*;
import static java.math.RoundingMode.HALF_UP;

/**
 * The AccountService Service class. 
 * 
 * @author dineshmetkari
 *
 */
@Service
public class AccountService {

	private ClientAccountRepository clientAccountRepository;

	private AtmAllocationRepository atmAllocationRepository;

	enum AccountTypeEnum {

		CHQ, SVGS, CCRD;
	}

	public AccountService(final ClientAccountRepository clientAccountRepository,
			final AtmAllocationRepository atmAllocationRepository) {
		this.clientAccountRepository = clientAccountRepository;
		this.atmAllocationRepository = atmAllocationRepository;
	}

	/**
	 * 
	 * @param id
	 * @param transactional
	 * @return
	 * @throws CustomException
	 */
	public List<AccountModel> getAccountBalances(Integer id, boolean transactional) throws CustomException {

		List<Object[]> clientAccounts = clientAccountRepository.findAllByClientId(id, transactional);

		if (clientAccounts.size() == 0) {
			throw new CustomException("The client does not have any qualifying accounts");
		}
		return clientAccounts.stream().map(AccountModel::mapToAccountDto).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param id
	 * @param transactional
	 * @return
	 * @throws CustomException
	 */
	public List<CurrencyAccountModel> getCurrencyAccounts(Integer id, boolean transactional) throws CustomException {
		List<Object[]> currencyAccounts = clientAccountRepository.findAllCurrencyAccountsByClientId(id, false);

		if (currencyAccounts.size() == 0) {
			throw new CustomException("No Account Found");
		}
		List<CurrencyAccountModel> responseList = new ArrayList<>();
		currencyAccounts.forEach(obj -> {
			BigDecimal convertedResult = convertToZAR(obj[3].toString(), new BigDecimal(obj[1].toString()),
					new BigDecimal(obj[4].toString()));
			responseList.add(mapToDto(obj, convertedResult));

		});
		return responseList;

	}

	/**
	 * 
	 * @param id
	 * @param accountWithdrawalRequestDto
	 * @return
	 * @throws CustomException
	 */
	public List<AtmDenominationMap> withdrawFromAcc(Integer id,
			AccountWithdrawalRequestModel accountWithdrawalRequestDto) throws CustomException {

		List<AtmDenominationMap> atmDenominationResponseDtoList;
		BigDecimal accountNewBalance;
		BigDecimal requestedAmount = accountWithdrawalRequestDto.getWithdrawalAmt();
		if (requestedAmount == null) {
			throw new CustomException("Please enter the amount");
		}
		ClientAccount clientAccountDetails = clientAccountRepository
				.findByClientAccountNumber(accountWithdrawalRequestDto.getAccountNumber());
		if (clientAccountDetails.getAccount_type_code().getAccount_type_code().equals(AccountTypeEnum.CHQ.toString())) {
			int i = clientAccountDetails.getDisplayBalance().subtract(accountWithdrawalRequestDto.getWithdrawalAmt())
					.intValue();
			if (clientAccountDetails.getDisplayBalance().compareTo(new BigDecimal(-10000)) == -1 || i < -10000) {
				throw new CustomException("Insufficient funds: You have exceeded your cheque account limit");
			}
			atmDenominationResponseDtoList = getAtmDispensation(accountWithdrawalRequestDto.getWithdrawalAmt(),
					accountWithdrawalRequestDto.getAtmId());
			BigDecimal totalPossibleSum = BigDecimal.ZERO;
			for (AtmDenominationMap dto : atmDenominationResponseDtoList) {
				totalPossibleSum = totalPossibleSum.add(dto.getKey().multiply(new BigDecimal(dto.getCount())));
			}
			if (totalPossibleSum.equals(requestedAmount)) {
				accountNewBalance = clientAccountDetails.getDisplayBalance()
						.subtract(accountWithdrawalRequestDto.getWithdrawalAmt());
				updateClientAccountBalance(accountNewBalance, clientAccountDetails.getClientAccountNumber());

			} else {
				throw new CustomException("Can not be dispansed Required Amount in Current ATM Denominations");
			}

		} else {
			if (clientAccountDetails.getDisplayBalance()
					.compareTo(accountWithdrawalRequestDto.getWithdrawalAmt()) == -1) {
				throw new CustomException("Insufficient funds");
			}
			atmDenominationResponseDtoList = getAtmDispensation(accountWithdrawalRequestDto.getWithdrawalAmt(),
					accountWithdrawalRequestDto.getAtmId());
			BigDecimal totalPossibleSum = BigDecimal.ZERO;
			for (AtmDenominationMap dto : atmDenominationResponseDtoList) {
				totalPossibleSum = totalPossibleSum.add(dto.getKey().multiply(new BigDecimal(dto.getCount())));
			}
			if (totalPossibleSum.equals(requestedAmount)) {
				accountNewBalance = clientAccountDetails.getDisplayBalance()
						.subtract(accountWithdrawalRequestDto.getWithdrawalAmt());
				updateClientAccountBalance(accountNewBalance, clientAccountDetails.getClientAccountNumber());

			} else {
				throw new CustomException("Can not be dispansed Required Amount in Current ATM Denominations");
			}
		}
		return atmDenominationResponseDtoList;
	}

	/**
	 * 
	 * @param requestAmt
	 * @param atmId
	 * @return
	 * @throws CustomException
	 */
	private List<AtmDenominationMap> getAtmDispensation(BigDecimal requestAmt, Integer atmId) throws CustomException {

		List<Object[]> atmAllocations = atmAllocationRepository.getListOfDenominationsAndSumByAtmId(atmId);

		if (atmAllocations.size() == 0) {
			throw new CustomException("ATM not registered or unfunded");
		}

		List<AtmAllocationModel> atmAllocationModelList = atmAllocations.stream()
				.map(AtmAllocationModel::mapToAtmAllocationModel).collect(Collectors.toList());

		BigDecimal atmAllocationSum = BigDecimal.ZERO;
		for (AtmAllocationModel atmAllocation : atmAllocationModelList) {
			atmAllocationSum = atmAllocationSum.add(atmAllocation.getDenominationSum());
		}

		if (requestAmt.compareTo((atmAllocationSum)) == 1) {
			throw new CustomException("Amount not available in  ATM, would you like to draw : " + atmAllocationSum);
		}

		List<AtmDenominationMap> atmDenominationResponseDtoList = new ArrayList<>();
		int countNotes = 0;
		BigDecimal tempDispenseBalance = requestAmt;
		for (AtmAllocationModel model : atmAllocationModelList) {
			if (model.getCount() > 0) {
				if (tempDispenseBalance.remainder(model.getValue()).compareTo(BigDecimal.ZERO) == 0) {

					countNotes = tempDispenseBalance.divide(model.getValue()).intValue();
					if (model.getCount() >= countNotes) {
						model.setCount(model.getCount() - countNotes);
						atmDenominationResponseDtoList.add(new AtmDenominationMap(model.getValue(), countNotes));
						tempDispenseBalance = BigDecimal.ZERO;
						break;

					} else {
						atmDenominationResponseDtoList.add(new AtmDenominationMap(model.getValue(), model.getCount()));
						model.setCount(0);
						tempDispenseBalance = tempDispenseBalance.subtract(model.getDenominationSum());
					}

				} else if (tempDispenseBalance.divide(model.getValue()).intValue() >= 1) {
					countNotes = tempDispenseBalance.divide(model.getValue()).intValue();
					atmDenominationResponseDtoList.add(new AtmDenominationMap(model.getValue(), countNotes));
					model.setCount(model.getCount() - countNotes);
					tempDispenseBalance = tempDispenseBalance
							.subtract(model.getValue().multiply(new BigDecimal(countNotes)));
				}
			}
		}

		return atmDenominationResponseDtoList;
	}

	private void updateClientAccountBalance(BigDecimal amountAfterWithdrawal, String clientAccNum) {
		ClientAccount clientAccount = clientAccountRepository.findByClientAccountNumber(clientAccNum);
		clientAccount.setDisplayBalance(amountAfterWithdrawal);
		clientAccountRepository.save(clientAccount);

	}

	private BigDecimal convertToZAR(String convIndicator, BigDecimal curBalance, BigDecimal convRate) {

		switch (convIndicator) {
		case "*":
			return curBalance.multiply(convRate);
		case "/":
			return curBalance.divide(convRate, HALF_UP);
		default:
			return new BigDecimal(0);
		}
	}
}
