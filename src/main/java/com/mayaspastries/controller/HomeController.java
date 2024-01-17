package com.mayaspastries.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mayaspastries.entities.Product;
import com.mayaspastries.entities.User;
import com.mayaspastries.service.ProductService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

	private ProductService serviceProduct;

	@GetMapping("/home")
	public String openHome(Model model) {
		List<Product> randomProducts = serviceProduct.getRandomProducts(5);

		model.addAttribute("randomProducts", randomProducts);

		return "index";
	}

	@GetMapping("/login")
	public String openLogin(Model model) {
		model.addAttribute("user", new User());
		return "login.html";
	}
}
