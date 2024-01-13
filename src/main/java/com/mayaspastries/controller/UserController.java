package com.mayaspastries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mayaspastries.entities.User;
import com.mayaspastries.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {

	private UserService serviceUser;

	@PostMapping("/login")
	public String validateLogin(@RequestParam("user") String user, @RequestParam("password") String passwod,
			Model model, HttpSession session) {

		User userGet = serviceUser.getUserAndPassword(user, passwod);

		if (userGet != null) {
			Integer userId = userGet.getIduser();
			session.setAttribute("userid", userId);

			return "redirect:/maintenance";

		} else {
			userGet = serviceUser.getUser(user, passwod);
		}
		if (userGet != null) {

			Integer userId = userGet.getIduser();
			session.setAttribute("userId", userId);

			return "redirect:/maintenance";
		} else {
			model.addAttribute("error", "Incorrect Credetentials");
			model.addAttribute("user", new User());
			return "login";
		}
	}
}
