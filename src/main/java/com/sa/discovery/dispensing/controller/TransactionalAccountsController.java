package com.sa.discovery.dispensing.controller;

import com.sa.discovery.dispensing.entity.ATM;
import com.sa.discovery.dispensing.entity.Client;
import com.sa.discovery.dispensing.exception.CustomException;
import com.sa.discovery.dispensing.model.AccountModel;
import com.sa.discovery.dispensing.model.AccountWithdrawalRequestModel;
import com.sa.discovery.dispensing.model.CurrencyAccountModel;
import com.sa.discovery.dispensing.repository.ATMRepository;
import com.sa.discovery.dispensing.repository.ClientRepository;
import com.sa.discovery.dispensing.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The class TransactionalAccountsController contains variou srest endpoints.
 * 
 * @author dineshmetkari
 *
 */
@Controller
public class TransactionalAccountsController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ATMRepository atmRepository;

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@PostMapping("/user/clientaccounts.html")
	public String clientAccounts2(@ModelAttribute("userid") int id, Model model) {
		try {
			List<AccountModel> accounts = accountService.getAccountBalances(id, true);
			model.addAttribute("accounts", accounts);
			Client client = clientRepository.findById(id).get();
			model.addAttribute("client", client);
		} catch (CustomException e) {
			model.addAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
			return "forward:/login.html";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
			e.printStackTrace();
			return "forward:/login.html";
		}
		return "user/clientaccounts";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/clientaccounts.html/{userid}")
	public String clientAccounts(@PathVariable("userid") int id, Model model) {
		try {
			List<AccountModel> accounts = accountService.getAccountBalances(id, true);
			model.addAttribute("accounts", accounts);
			Client client = clientRepository.findById(id).get();
			model.addAttribute("client", client);
		} catch (CustomException e) {
			model.addAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
			return "login";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
			e.printStackTrace();
			return "login";
		}
		return "user/clientaccounts";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/currencyrandvalues.html/{userid}")
	public String currencyAccounts(@PathVariable("userid") int id, Model model) {
		try {
			List<CurrencyAccountModel> currencyAccounts = accountService.getCurrencyAccounts(id, true);
			model.addAttribute("currencyAccounts", currencyAccounts);
			Client client = clientRepository.findById(id).get();
			model.addAttribute("client", client);
		} catch (CustomException e) {
			model.addAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
			e.printStackTrace();
		}
		return "user/currencyrandvalues";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/withdraw.html/{userid}")
	public String withdraw(@PathVariable("userid") int id, Model model) {
		try {
			List<AccountModel> accounts = accountService.getAccountBalances(id, true);
			model.addAttribute("accounts", accounts);
			List<ATM> atms = atmRepository.findAll();
			model.addAttribute("atms", atms);
			Client client = clientRepository.findById(id).get();
			model.addAttribute("client", client);
			if (accounts.size() > 0) {
				model.addAttribute("selectedAccountNumber", accounts.get(0).getAccountNumber());
				model.addAttribute("selectedAccountType", accounts.get(0).getAccountType());
			}
			if (atms.size() > 0) {
				model.addAttribute("selecedATM", atms.get(0).getAtm_id());
			}
		} catch (CustomException e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
			e.printStackTrace();
		}
		return "user/withdraw";
	}

	/**
	 * 
	 * @param id
	 * @param dto
	 * @param model
	 * @return
	 */
	@PostMapping("/user/withdraw.html/{userid}")
	public String withdrawAmount(@PathVariable("userid") int id,
			@ModelAttribute("withdrawForm") AccountWithdrawalRequestModel dto, Model model) {
		try {
			List<AccountModel> accounts = accountService.getAccountBalances(id, true);
			model.addAttribute("accounts", accounts);
			List<ATM> atms = atmRepository.findAll();
			model.addAttribute("atms", atms);
			model.addAttribute("selectedAccountNumber", dto.getAccountNumber());
			model.addAttribute("selecedATM", dto.getAtmId());
			Client client = clientRepository.findById(id).get();
			model.addAttribute("client", client);
			if (accounts.size() > 0) {
				model.addAttribute("selectedAccountNumber", dto.getAccountNumber());
				AccountModel acct = accounts.stream().filter(s -> s.getAccountNumber().equals(dto.getAccountNumber()))
						.findFirst().get();
				model.addAttribute("selectedAccountType", acct.getAccountType());
			}
			if (atms.size() > 0) {
				model.addAttribute("selecedATM", dto.getAtmId());
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
		}
		return "user/withdraw";
	}

	/**
	 * 
	 * @param id
	 * @param dto
	 * @param model
	 * @return
	 */
	@PostMapping("/user/withdrawaction/{userid}")
	public String withdrawAction(@PathVariable("userid") int id,
			@ModelAttribute("withdrawForm") AccountWithdrawalRequestModel dto, Model model) {
		try {
			List<AccountModel> accounts = accountService.getAccountBalances(id, true);
			model.addAttribute("accounts", accounts);
			List<ATM> atms = atmRepository.findAll();
			model.addAttribute("atms", atms);
			model.addAttribute("selectedAccountNumber", dto.getAccountNumber());
			Client client = clientRepository.findById(id).get();
			if (accounts.size() > 0) {
				model.addAttribute("selectedAccountNumber", accounts.get(0).getAccountNumber());
				AccountModel acct = accounts.stream().filter(s -> s.getAccountNumber().equals(dto.getAccountNumber()))
						.findFirst().get();
				model.addAttribute("selectedAccountType", acct.getAccountType());
			}
			if (atms.size() > 0) {
				model.addAttribute("selecedATM", atms.get(0).getAtm_id());
			}
			model.addAttribute("client", client);
			model.addAttribute("errorMessage", "Successfully Deducted " + dto.getWithdrawalAmt() + " from Account");
			accountService.withdrawFromAcc(id, dto);
		} catch (CustomException e) {
			model.addAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
			e.printStackTrace();
		}
		return "user/withdraw";
	}
}
