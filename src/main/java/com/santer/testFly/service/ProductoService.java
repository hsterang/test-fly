package com.santer.testFly.service;

import com.santer.testFly.entity.EstadoCuenta;
import com.santer.testFly.entity.Producto;
import com.santer.testFly.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findByClienteNumeroIdentificacion(id);
    }

    public Producto crearProducto(Producto producto) {
        if (producto.getSaldo() < 0) {
            throw new IllegalArgumentException("El saldo inicial de la cuenta de ahorros no puede ser menor que cero.");
        }
        producto.setEstado(EstadoCuenta.ACTIVA);
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            producto.setId(id);
            return productoRepository.save(producto);
        } else {
            return null;
        }
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}