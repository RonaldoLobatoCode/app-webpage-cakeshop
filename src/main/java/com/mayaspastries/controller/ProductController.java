package com.mayaspastries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mayaspastries.entities.Product;
import com.mayaspastries.service.CategoryService;
import com.mayaspastries.service.EmployeeService;
import com.mayaspastries.service.IUploadFileService;
import com.mayaspastries.service.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ProductController {

    private CategoryService serviceCategory;
    private EmployeeService serviceEmployee;
    private ProductService serviceProduct;
    private IUploadFileService uploadFileService;

    @GetMapping("/maintenance")
    public String listProduct(@ModelAttribute Product product, Model model, HttpSession session) {

        Integer currentUserId = (Integer) session.getAttribute("userId");

        if (currentUserId != null) {

            int userId = currentUserId;

            Integer employeeId = serviceEmployee.getEmployeeIdByUsername(userId);

            if (employeeId != null) {
                model.addAttribute("listCategory", serviceCategory.listCategory());
                model.addAttribute("listProduct", serviceProduct.listProduct());
                model.addAttribute("product", new Product());
                model.addAttribute("employeeId", employeeId);
                return "maintenance-product";
            }
        }
        return "redirect:/login";

    }

}
