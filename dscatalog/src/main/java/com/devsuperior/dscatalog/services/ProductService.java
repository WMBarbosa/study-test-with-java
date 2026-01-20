package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mappers.ProductMapper;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable){
        Page<Product> category = productRepository.searchByName(name, pageable);
        return category.map(productMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO){
        Product product = productMapper.toEntity(productDTO, categoryRepository);
        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id){
        if (!productRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation - Category id: " + id);
        }
    }

    @Transactional
    public ProductDTO update (Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        productMapper.updateEntityFromDto(productDTO, product, categoryRepository);
        return productMapper.toDTO(productRepository.save(product));
    }
}
