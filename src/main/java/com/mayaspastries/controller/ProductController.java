package com.mayaspastries.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(value = "/uploads/{filename}")
    public ResponseEntity<Resource> goImage(@PathVariable String filename) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
