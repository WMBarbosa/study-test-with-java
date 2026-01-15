package com.barbosa.estudo.service;

import com.barbosa.estudo.dto.CategoryDTO;
import com.barbosa.estudo.entities.Category;
import com.barbosa.estudo.mappers.CategoryMapper;
import com.barbosa.estudo.repositories.CategoryRepository;
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
                .orElseThrow(() -> new RuntimeException("Category not found"));
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
            throw new RuntimeException("Category not found");
        }
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Error while deleting category");
        }
    }

    @Transactional
    public CategoryDTO update (Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toDTO(categoryRepository.save(category));
    }
}
