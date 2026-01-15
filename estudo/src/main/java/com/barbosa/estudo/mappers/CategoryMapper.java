package com.barbosa.estudo.mappers;

import com.barbosa.estudo.dto.CategoryDTO;
import com.barbosa.estudo.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category entity);

    Category toEntity(CategoryDTO dto);

}
