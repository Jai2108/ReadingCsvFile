package com.example.productService.controller;

import com.example.productService.exception.CsvFileNotFoundException;
import com.example.productService.exception.ProductNotFoundException;
import com.example.productService.model.Product;
import com.example.productService.responseDto.ProductAndStockDto;
import com.example.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    public String addProduct(@RequestBody Product product){

        productService.addProduct(product);
        return "product added succesfuly";
    }

    @GetMapping("/product-stock-byId/{productId}")
    public ResponseEntity<ProductAndStockDto> getById(@PathVariable int productId) {
        try {
            ProductAndStockDto productAndStockDto = productService.getById(productId);
            return ResponseEntity.ok(productAndStockDto);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (CsvFileNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
