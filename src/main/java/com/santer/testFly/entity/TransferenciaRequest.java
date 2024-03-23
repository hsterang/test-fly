package com.santer.testFly.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferenciaRequest {
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private double monto;
}
