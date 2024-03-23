package com.santer.testFly.controller;

import com.santer.testFly.entity.Transaccion;
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

    @GetMapping
    public ResponseEntity<List<Transaccion>> obtenerTodasTransacciones() {
        List<Transaccion> transacciones = transaccionService.obtenerTodasTransacciones();
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        Transaccion nuevaTransaccion = transaccionService.crearTransaccion(transaccion);
        return new ResponseEntity<>(nuevaTransaccion, HttpStatus.CREATED);
    }
}