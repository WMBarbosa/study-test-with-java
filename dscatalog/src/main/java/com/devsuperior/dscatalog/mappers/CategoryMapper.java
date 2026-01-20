package com.devsuperior.dscatalog.mappers;


import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
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
