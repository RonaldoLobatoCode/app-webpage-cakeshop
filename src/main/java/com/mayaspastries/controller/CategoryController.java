package com.mayaspastries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mayaspastries.entities.Category;
import com.mayaspastries.service.CategoryService;
import com.mayaspastries.service.EmployeeService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CategoryController {

    private CategoryService serviceCategory;

    private EmployeeService serviceEmployee;

    @GetMapping("/maintenance/category")
    public String openPageCategory(Model model, HttpSession session) {

        Integer currentUserId = (Integer) session.getAttribute("userId");

        if (currentUserId != null) {
            int userId = currentUserId;

            Integer employeeId = serviceEmployee.getEmployeeIdByUsername(userId);

            if (employeeId != null) {
                model.addAttribute("listCategory", serviceCategory.listCategory());
                model.addAttribute("category", new Category());
                model.addAttribute("employeeId", employeeId);
                return "maintenance-category";

            }
        }
        return "redirect:/login";
    }
}
