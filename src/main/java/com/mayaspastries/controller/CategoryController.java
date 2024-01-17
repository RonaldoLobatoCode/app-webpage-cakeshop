package com.mayaspastries.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.mayaspastries.entities.Category;
import com.mayaspastries.service.CategoryService;
import com.mayaspastries.service.EmployeeService;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
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

    @GetMapping("/search/category/{id}")
    public ResponseEntity<Category> searchIdCategory(@PathVariable("id") Integer id, Model model) {
        Category category = serviceCategory.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/mayas/register/category")
    public String addCategory(@ModelAttribute("category") Category category) {
        serviceCategory.addCategory(category);
        return "redirect:/maintenance/category";
    }

    @PutMapping("/category")
    public ResponseEntity<String> updateCategory(@ModelAttribute("category") Category category) {
        serviceCategory.updateCategory(category);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
