package com.mayaspastries.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mayaspastries.entities.Product;
import com.mayaspastries.service.CategoryService;
import com.mayaspastries.service.EmployeeService;
import com.mayaspastries.service.IUploadFileService;
import com.mayaspastries.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/mayas/add/product")
    public String addProduct(@Validated @ModelAttribute("product") Product product, BindingResult result, Model model,
            @RequestParam("file") MultipartFile image, RedirectAttributes flash, SessionStatus status,
            @RequestParam("employeeId") Integer employeeId)
            throws Exception {

        if (result.hasErrors()) {
            System.out.println(result.getFieldError());
            return "maintenance-product";
        } else {
            if (!image.isEmpty()) {
                if (product.getIdproduct() > 0 && product.getImage() != null && product.getImage().length() > 0) {
                    uploadFileService.delete(product.getImage());
                }
                String uniqueFileName = uploadFileService.copy(image);
                product.setImage(uniqueFileName);
            }

            product.setIdemployee(employeeId);

            serviceProduct.addProduct(product);
            status.setComplete();
        }
        return "redirect:/maintenance";
    }

    @GetMapping("/filter")
    public String filterProduct(@Param("searchWord") String searchWord, @ModelAttribute Product product, Model model,
            HttpServletRequest request, HttpSession session) {

        Integer currentUserId = (Integer) session.getAttribute("userId");

        if (currentUserId != null) {

            int userId = currentUserId;

            Integer employeeId = serviceEmployee.getEmployeeIdByUsername(userId);

            model.addAttribute("searchWord", searchWord);
            model.addAttribute("product", new Product());
            model.addAttribute("listProduct", serviceProduct.findProductsByName(searchWord));
            model.addAttribute("listCategory", serviceCategory.listCategory());
            model.addAttribute("employeeId", employeeId);

            return "maintenance-product";

        }

        return "maintenance-product";
    }

    @GetMapping("/letter")
    public String showLetterPage(Model model, @RequestParam(value = "searchWord", required = false) String searchWord) {
        model.addAttribute("product", new Product());
        model.addAttribute("listCategory", serviceCategory.listCategory());

        if (searchWord != null && !searchWord.isEmpty()) {
            model.addAttribute("searchWord", searchWord);
            model.addAttribute("listProduct", serviceProduct.findProductsByName(searchWord));
        } else {
            model.addAttribute("searchWord", "");
            model.addAttribute("listProduct", serviceProduct.listProduct());
        }
        return "letter";
    }
}
