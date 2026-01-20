package com.devsuperior.dscatalog.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;


}
