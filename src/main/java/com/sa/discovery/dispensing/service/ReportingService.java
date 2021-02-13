package com.sa.discovery.dispensing.service;

import org.springframework.stereotype.Service;

import com.sa.discovery.dispensing.entity.Client;
import com.sa.discovery.dispensing.exception.CustomException;
import com.sa.discovery.dispensing.model.AccountReportModel;
import com.sa.discovery.dispensing.model.AggregateReportModel;
import com.sa.discovery.dispensing.repository.ClientAccountRepository;
import com.sa.discovery.dispensing.repository.ClientRepository;
import com.sa.discovery.dispensing.repository.ReportingRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The ReportingService service class. 
 * 
 * @author dineshmetkari
 *
 */
@Service
public class ReportingService {

	private ReportingRepository reportingRepository;

	private ClientAccountRepository clientAccountRepository;

	private ClientRepository clientRepository;

	public ReportingService(ReportingRepository reportingRepository, ClientAccountRepository clientAccountRepository,
			ClientRepository clientRepository) {
		this.reportingRepository = reportingRepository;
		this.clientRepository = clientRepository;
		this.clientAccountRepository = clientAccountRepository;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws CustomException
	 */
	public AggregateReportModel getAggregateReport(Integer id) throws CustomException {

		Client client = clientRepository.findById(id).get();
		if (client == null) {
			throw new CustomException("Cleint Not Found");
		}
		AggregateReportModel aggregateReportDto = new AggregateReportModel();
		aggregateReportDto.setClientTitle(client.getTitle());
		aggregateReportDto.setClientName(client.getName());
		aggregateReportDto.setClientSurname(client.getSurname());
		aggregateReportDto.setTransactionalAccBalance(
				new BigDecimal(reportingRepository.findSumOfTransactionalAccById(id).toString()));
		aggregateReportDto.setLoanAccBalance(new BigDecimal(reportingRepository.findSumOfLoanAccById(id).toString()));
		aggregateReportDto.setNetPosition(
				aggregateReportDto.getTransactionalAccBalance().add(aggregateReportDto.getLoanAccBalance()));
		return aggregateReportDto;
	}

	/**
	 * 
	 * @param transactional
	 * @return
	 * @throws CustomException
	 */
	public List<AccountReportModel> getHighestTransactionalAccountOfUsers(boolean transactional)
			throws CustomException {

		List<Object[]> list = reportingRepository.findAllTransactionalAccountsPerClientId(transactional);
		if (list.size() == 0) {
			throw new CustomException("No Transactions Found");
		}
		return list.stream().map(AccountReportModel::mapToAccountReportDto).collect(Collectors.toList());
	}
}
