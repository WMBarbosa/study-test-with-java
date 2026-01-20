package com.devsuperior.dscatalog.controller;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService; // mock do service

    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private Long existingId;
    private Long nonExistingId;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(productDTO));
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        when(productService.findAll(anyString(), any(Pageable.class)))
                .thenReturn(page);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnProductDTOWhenIdExists() throws Exception {
        when(productService.findById(existingId)).thenReturn(productDTO);

        mockMvc.perform(get("/products/{id}", existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingId))
                .andExpect(jsonPath("$.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.price").value(productDTO.getPrice()));
    }

    @Test
    void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        when(productService.findById(nonExistingId))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/products/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateShouldUpdateProductWhenIdExists() throws Exception {
        when(productService.update(eq(existingId), any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(put("/products/{id}", existingId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productDTO.getId()))
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }

    @Test
    void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        when(productService.update(eq(nonExistingId), any(ProductDTO.class)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/products/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldDeleteProductWhenIdExists() throws Exception {
        doNothing().when(productService).delete(existingId);

        mockMvc.perform(delete("/products/{id}", existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        doThrow(ResourceNotFoundException.class).when(productService).delete(nonExistingId);

        mockMvc.perform(delete("/products/{id}", nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturnDataBaseExceptionWhenIdExists() throws Exception {
        doThrow(DatabaseException.class).when(productService).delete(existingId);

        mockMvc.perform(delete("/products/{id}", existingId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveShouldReturnNewProductWhenIdIsNull() throws Exception {
        when(productService.save(any(ProductDTO.class))).thenReturn(productDTO);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(productDTO.getId()))
                .andExpect(jsonPath("$.name").value(productDTO.getName()));
    }
}
