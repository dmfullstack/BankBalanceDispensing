package com.sa.discovery.dispensing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sa.discovery.dispensing.entity.Client;
import com.sa.discovery.dispensing.exception.CustomException;
import com.sa.discovery.dispensing.model.AccountReportModel;
import com.sa.discovery.dispensing.model.AggregateReportModel;
import com.sa.discovery.dispensing.repository.ClientRepository;
import com.sa.discovery.dispensing.service.ReportingService;

import java.util.List;

/**
 * The class ReportingController containts report related rest endpoints.
 * 
 * @author dineshmetkari
 *
 */
@Controller
public class ReportingController {
	
	@Autowired
	private ReportingService reportingService;
	
	
	@Autowired
	private ClientRepository clientRepository;
	
	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/system/highestAccounts.html/{userid}")
    public String highestAccounts(@ModelAttribute("userid")int id,Model model) {
    	try {
    		List<AccountReportModel> accountReports = reportingService.getHighestTransactionalAccountOfUsers(true);
    		model.addAttribute("accountReports", accountReports);
    		  
			Client client = clientRepository.findById(id).get();
	    	model.addAttribute("client", client);
		} catch (CustomException e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
			e.printStackTrace();
		}
        return "system/highestAccounts";
    }
	
	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/system/aggregateFinancial.html/{userid}")
    public String aggregateFinancial(@ModelAttribute("userid")int id,Model model) {
    	try {
    		AggregateReportModel aggregateReport = reportingService.getAggregateReport(id);
    		model.addAttribute("aggregateReport", aggregateReport);
    		  
			Client client = clientRepository.findById(id).get();
	    	model.addAttribute("client", client);
		} catch (CustomException e) {
			model.addAttribute("errorMessage", "Encounterd Unexpected error");
			e.printStackTrace();
		}
        return "system/aggregateFinancial";
    }
    
}
