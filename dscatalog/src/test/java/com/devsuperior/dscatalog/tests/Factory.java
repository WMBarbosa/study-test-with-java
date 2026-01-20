package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mappers.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Factory {

    private static final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/phone.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(1L, "electronics"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product cr = createProduct();
        return mapper.toDTO(cr);
    }
}
