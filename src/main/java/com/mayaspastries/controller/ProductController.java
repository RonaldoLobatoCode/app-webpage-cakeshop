package com.mayaspastries.controller;

import java.io.File;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/search/{id}")
    public ResponseEntity<Product> searchIdProduct(@PathVariable("id") Integer id, Model model) {

        Product product = serviceProduct.getProductById(id);

        String currenImageInfo = product.getImage();
        model.addAttribute("currentImageInfo", currenImageInfo);
        System.out.println(currenImageInfo);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/maintenance")
    public ResponseEntity<String> updateProduct(@Validated @ModelAttribute("product") Product updatedProduct,
            BindingResult result, Model model,
            @RequestParam(value = "updatefile", required = false) MultipartFile image,
            RedirectAttributes flash, SessionStatus status) throws Exception {
        if (result.hasErrors()) {
            System.out.println("Errores de validación: " + result.getAllErrors());
            return new ResponseEntity<>("Error de validación", HttpStatus.BAD_REQUEST);
        } else {
            System.out.println("Producto actualizado con éxito");
            Product existingProduct = serviceProduct.getProductById(updatedProduct.getIdproduct());
            System.out.println("Obteniendo el ID " + existingProduct);

            if (existingProduct == null) {
                System.out.println("No se encontró el producto existente");
                return new ResponseEntity<>("No se encontró el producto existente", HttpStatus.NOT_FOUND);
            }

            String previosImageFileName = existingProduct.getImage();

            if (image != null && !image.isEmpty()) {
                String uniqueFileName = uploadFileService.copy(image);

                String newImageFileName = uniqueFileName;

                if (!newImageFileName.equals(previosImageFileName)) {
                    File previosImageFile = new File("uploads/" + previosImageFileName);
                    if (previosImageFile.exists()) {
                        previosImageFile.delete();
                    }
                }
                existingProduct.setImage(uniqueFileName);
            }
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setIdcategory(updatedProduct.getIdcategory());

            serviceProduct.updateProduct(existingProduct);
            status.setComplete();

            System.out.println("Actualización del producto completada");
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }

    @GetMapping("/delete/{idproduct}")
    public String deleteProduct(@PathVariable("idproduct") Integer idproduct, Model model) {

        Product product = serviceProduct.getProductById(idproduct);
        String imageName = product.getImage();

        serviceProduct.deleteProduct(idproduct);

        if (imageName != null) {
            String imagePath = "uploads/" + imageName;

            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }

        return "redirect:/maintenance";
    }

    @GetMapping("/")
    public String getMenuPage(Model model) {
        List<Product> randomProducts = serviceProduct.getRandomProducts(5);

        model.addAttribute("randomProducts", randomProducts);

        return "index";
    }

    @GetMapping("/detail/{idproduct}")
    public String getMenuPageDetail(@PathVariable Integer idproduct, Model model) {
        Product product = serviceProduct.getProductById(idproduct);

        model.addAttribute("product", product);

        List<Product> randomProducts = serviceProduct.getRandomProducts(3);
        model.addAttribute("randomProductsDetail", randomProducts);

        return "detail";
    }
}
