package com.mayaspastries.controller;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mayaspastries.entities.User;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String openHome(Model model) {
		return "index";
	}

	@GetMapping("/login")
	public String openLogin(Model model) {
		model.addAttribute("user", new User());
		return "login.html";
	}
}
