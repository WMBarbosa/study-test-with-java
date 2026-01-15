package com.barbosa.estudo.mappers;

import com.barbosa.estudo.dto.CategoryDTO;
import com.barbosa.estudo.entities.Category;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(
            CategoryDTO dto,
            @MappingTarget Category entity
    );

    Category toEntity(CategoryDTO dto);

}
