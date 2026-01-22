package com.devsuperior.dscatalog.tests;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Component
public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/phone.png", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(new Category(1L, "electronics"));
        return product;
    }

    /*
    Fazendo a conversão na mão de entidade para DTO, para testes isolados das camadas
    ex: Service, Controller e repository, funciona assim,
    mas o correto para esses testes é o construtor abaixo
     */
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

    /*
    Conversão de entidade para DTO que nesse casó seria o correto com construtores
    (No testes não é usado o  MapStruct)
    Em teoria, quando não usamos algum framework esse seria o metodo para essa conversão,
    dependendo obviamente de como as entidades foram criadas com seus devidos relacionamentos
    */
    public static ProductDTO createProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date, Set<CategoryDTO> categories){
        ProductDTO dto = new ProductDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setPrice(price);
        dto.setImgUrl(imgUrl);
        dto.setDate(date);
        categories.forEach(c -> dto.getCategories().add(c));

        return dto;
    }

}
