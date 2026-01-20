package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.mappers.CategoryMapper;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(String name, Pageable pageable){
        Page<Category> category = categoryRepository.searchByName(name, pageable);
        return category.map(categoryMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO){
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id){
        if (!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e){
            throw new DatabaseException("Integrity violation - Category id: " + id);
        }
    }

    @Transactional
    public CategoryDTO update (Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        categoryMapper.updateEntityFromDto(categoryDTO, category);
        return categoryMapper.toDTO(categoryRepository.save(category));
    }
}

