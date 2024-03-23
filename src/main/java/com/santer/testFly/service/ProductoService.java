package com.santer.testFly.service;

import com.santer.testFly.entity.EstadoCuenta;
import com.santer.testFly.entity.Producto;
import com.santer.testFly.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerProductoPorId(String id) {
        log.info("Obteniendo productos por ID de cliente: {}", id);
        List<Producto> productos = productoRepository.findByClienteNumeroIdentificacion(id);
        log.info("Productos obtenidos para el cliente con ID: {}", id);
        return productos;
    }

    public Producto crearProducto(Producto producto) {
        log.info("Creando nuevo producto: {}", producto);
        if (producto.getSaldo() < 0) {
            throw new IllegalArgumentException("El saldo inicial de la cuenta de ahorros no puede ser menor que cero.");
        }
        producto.setEstado(EstadoCuenta.ACTIVA);
        Producto nuevoProducto = productoRepository.save(producto);
        log.info("Producto creado: {}", nuevoProducto);
        return nuevoProducto;
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        log.info("Actualizando producto con ID: {}", id);
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            producto.setId(id);
            Producto productoActualizado = productoRepository.save(producto);
            log.info("Producto actualizado: {}", productoActualizado);
            return productoActualizado;
        } else {
            log.warn("No se encontró producto para actualizar con ID: {}", id);
            return null;
        }
    }

    public void eliminarProducto(Long id) {
        log.info("Eliminando producto con ID: {}", id);
        productoRepository.deleteById(id);
        log.info("Producto eliminado con ID: {}", id);
    }
}