package com.santer.testFly.entity;

import lombok.Data;

@Data
public class TransferenciaRequest {
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private double monto;
}
