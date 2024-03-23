package com.santer.testFly.entity;

import lombok.Builder;

@Builder
public class ErrorEntity {
    private int status;
    private String message;
}
