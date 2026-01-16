package com.barbosa.estudo.mappers;

import com.barbosa.estudo.dto.CategoryDTO;
import com.barbosa.estudo.dto.ProductDTO;
import com.barbosa.estudo.entities.Category;
import com.barbosa.estudo.entities.Product;
import com.barbosa.estudo.repositories.CategoryRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {

    @Mapping(target = "categories", source = "categories")
    ProductDTO toDTO(Product product);


    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductDTO dto,
                             @MappingTarget Product entity,
                             @Context CategoryRepository categoryRepository
    );

    Product toEntity(ProductDTO dto, @Context CategoryRepository categoryRepository);


    @AfterMapping
    default void mapCategories(
            ProductDTO productDTO,
            @MappingTarget Product product,
            @Context CategoryRepository categoryRepository
            ) {

        product.getCategories().clear();

        List<Long> ids = productDTO.getCategories()
                .stream()
                .map(CategoryDTO::getId)
                .toList();

        List<Category> categories = categoryRepository.findAllById(ids);

        if (categories.size() != ids.size()) {
            throw new IllegalArgumentException("Invalid category id");
        }

        product.getCategories().addAll(categories);
    }
}
