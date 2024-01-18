package com.mayaspastries.controller;

import org.springframework.stereotype.Controller;

import com.mayaspastries.service.CategoryService;
import com.mayaspastries.service.EmployeeService;
import com.mayaspastries.service.IUploadFileService;
import com.mayaspastries.service.ProductService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ProductController {

    private CategoryService serviceCategory;
    private EmployeeService serviceEmployee;
    private ProductService serviceProduct;
    private IUploadFileService uploadFileService;
    

}
