package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/phone.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(1L, "electronics"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        ProductDTO dto = new ProductDTO();
        dto.setId(1L);
        dto.setName("Phone");
        dto.setDescription("Good Phone");
        dto.setPrice(800.0);
        dto.setImgUrl("https://img.com/phone.png");
        dto.setDate(Instant.parse("2020-10-20T03:00:00Z"));
        dto.getCategories().add(new CategoryDTO(1L, "electronics"));
        return dto;
    }

}
