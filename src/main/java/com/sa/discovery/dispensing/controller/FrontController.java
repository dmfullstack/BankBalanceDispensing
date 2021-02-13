package com.sa.discovery.dispensing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sa.discovery.dispensing.entity.Client;
import com.sa.discovery.dispensing.model.UserModel;
import com.sa.discovery.dispensing.repository.ATMRepository;
import com.sa.discovery.dispensing.repository.ClientRepository;
import com.sa.discovery.dispensing.service.AccountService;

import java.util.List;

/**
 * The class FrontController is the controller class contains rest endpoints. 
 * 
 * @author dineshmetkari
 *
 */
@Controller
public class FrontController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ATMRepository atmRepository;

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String root() {
		return "redirect:/login.html";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/login.html")
	public String login(Model model) {
		List<Client> clientList = clientRepository.findAll();
		model.addAttribute("clients", clientList);

		return "login";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/index.html")
	public String index(Model model) {

		List<Client> clientList = clientRepository.findAll();
		model.addAttribute("clients", clientList);
		return "index";
	}

	/**
	 * 
	 * @param userForm
	 * @param model
	 * @return
	 */
	@PostMapping("/index.html")
	public String navigate(@ModelAttribute("userid") UserModel userForm, Model model) {
		Client client = clientRepository.findById(new Integer(userForm.getUserid())).get();
		model.addAttribute("client", client);

		return "forward:user/index.html";
	}

}
