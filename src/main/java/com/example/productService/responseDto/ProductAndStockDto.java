package com.example.productService.responseDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAndStockDto {

    private int productId;

    private String productName;

    private int stockId;

    private  Long stocks;

}
