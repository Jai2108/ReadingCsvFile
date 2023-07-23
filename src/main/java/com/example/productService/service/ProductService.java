package com.example.productService.service;

import com.example.productService.exception.CsvFileNotFoundException;
import com.example.productService.exception.ProductNotFoundException;
import com.example.productService.model.Product;
import com.example.productService.repository.ProductRepository;
import com.example.productService.responseDto.ProductAndStockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void addProduct(Product product) {
        productRepository.save(product);

    }

    public ProductAndStockDto getById(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException("Product with ID " + productId + " not found."));

        String productName = product.getProductName();
        CSVRecord csvRecord = null;
        try {
            csvRecord = getCsvRecord(productName);
        } catch (IOException ex) {
            throw new CsvFileNotFoundException("Error reading CSV file: " + ex.getMessage());
        }

        if (csvRecord == null) {
            throw new CsvFileNotFoundException("No stock data found for product: " + productName);
        }

        ProductAndStockDto productAndStockDto = new ProductAndStockDto();
        productAndStockDto.setStocks(Long.valueOf(csvRecord.get("stocks")));
        productAndStockDto.setStockId(Integer.parseInt(csvRecord.get("id")));
        productAndStockDto.setProductId(product.getProductId());
        productAndStockDto.setProductName(product.getProductName());

        return productAndStockDto;
    }

    public static CSVRecord getCsvRecord(String productName) throws IOException {
        String path = "/home/zakapps/Downloads/csvfile/productStockData.csv";
        try (FileReader fileReader = new FileReader(path);
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                String stockName = (csvRecord.get("name"));
                if (stockName.equals(productName)) {

                    return csvRecord;

                }
            }
        }

         return null;
    }
}
