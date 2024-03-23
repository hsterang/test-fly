package com.santer.testFly.service;

import com.santer.testFly.entity.Transaccion;
import com.santer.testFly.repository.ProductoRepository;
import com.santer.testFly.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;
    public TransaccionService(TransaccionRepository transaccionRepository, ProductoRepository productoRepository) {
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
    }

    public List<Transaccion> obtenerTodasTransacciones() {
        return transaccionRepository.findAll();
    }

    public Transaccion crearTransaccion(Transaccion transaccion) {

        return transaccionRepository.save(transaccion);
    }
}