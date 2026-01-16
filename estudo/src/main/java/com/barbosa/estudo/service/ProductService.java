package com.barbosa.estudo.service;

import com.barbosa.estudo.dto.CategoryDTO;
import com.barbosa.estudo.dto.ProductDTO;
import com.barbosa.estudo.entities.Category;
import com.barbosa.estudo.entities.Product;
import com.barbosa.estudo.mappers.ProductMapper;
import com.barbosa.estudo.repositories.CategoryRepository;
import com.barbosa.estudo.repositories.ProductRepository;
import com.barbosa.estudo.service.serviceException.DataBaseException;
import com.barbosa.estudo.service.serviceException.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
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
        } catch (Exception e){
            throw new DataBaseException("Integrity violation - Category id: " + id);
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
