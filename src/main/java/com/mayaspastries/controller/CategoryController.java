package com.mayaspastries.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/delete/category/{idcategory}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(@PathVariable("idcategory") Integer idcategory) {
        boolean hasProduct = serviceCategory.hasProduct(idcategory);
        Map<String, Boolean> response = new HashMap<>();

        if (hasProduct) {
            response.put("canDelete", false);
            System.out.println("No se elimina");

        } else {
            serviceCategory.deleteCategory(idcategory);
            response.put("canDelete", true);
            System.out.println("Se elimina..");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter/category")
    public String filterCategory(@RequestParam("searchWord") String searchWord, Model model) {
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("listCategory", serviceCategory.findCategoryByName(searchWord));
        model.addAttribute("category", new Category());
        return "maintenance-category";
    }
}
