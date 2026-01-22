package com.devsuperior.dscatalog.controller;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private Long existingId;
    private Long nonExistingId;
    private Long countProducts;
    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countProducts = 25L;
        productDTO = Factory.createProductDTO();
    }


    @Test
    public void findAllShouldReturnSortedPageWhenSortByName() throws Exception{
        mockMvc.perform(get("/products?page=0&size=12&sort=name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(countProducts))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].name").value("Macbook Pro"))
        ;
    }

    @Test
    public void updateShouldUpdateProductWhenIdExists() throws Exception {

        mockMvc.perform(put("/products/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productDTO.getId()))
                .andExpect(jsonPath("$.name").value(productDTO.getName()))
                .andExpect(jsonPath("$.description").value(productDTO.getDescription()));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(put("/products/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isNotFound());
    }
}
