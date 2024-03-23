package com.santer.testFly.controller;

import com.santer.testFly.entity.Transaccion;
import com.santer.testFly.entity.TransferenciaRequest;
import com.santer.testFly.service.TransaccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/consignar")
    public void consignar(@RequestBody TransferenciaRequest request) {
        transaccionService.consignar(request.getNumeroCuentaOrigen(), request.getMonto());
    }

    @PostMapping("/retirar")
    public void retirar(@RequestBody TransferenciaRequest request) {
        transaccionService.retirar(request.getNumeroCuentaOrigen(), request.getMonto());
    }

    @PostMapping("/transferir")
    public void transferir(@RequestBody TransferenciaRequest request) {
        transaccionService.transferir(request.getNumeroCuentaOrigen(), request.getNumeroCuentaDestino(), request.getMonto());
    }
}