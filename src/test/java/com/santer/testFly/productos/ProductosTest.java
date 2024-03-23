package com.santer.testFly.productos;

import com.santer.testFly.controller.ProductoController;
import com.santer.testFly.entity.Cliente;
import com.santer.testFly.entity.Producto;
import com.santer.testFly.entity.TipoCuenta;
import com.santer.testFly.repository.ProductoRepository;
import com.santer.testFly.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductosTest {
    private ProductoRepository productoRepository = mock(ProductoRepository.class);
    private ProductoService productoService = new ProductoService(productoRepository);
    private ProductoController productoController = new ProductoController(productoService);

    @Test
    void testCrearProductoConSaldoPositivo() {
        // Arrange
        Producto producto = mockProducto();
        when(productoRepository.save(any())).thenReturn(producto);
        // Act
        ResponseEntity<Producto> response = productoController.crearProducto(producto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void testActualizarProduct() {
        // Arrange
        Producto producto = mockProducto();
        when(productoRepository.findById(any())).thenReturn(Optional.of(producto));
        when(productoRepository.save(any())).thenReturn(producto);
        // Act
        ResponseEntity<Producto> response = productoController.actualizarProducto(1L, producto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        doNothing().when(productoRepository).deleteById(any());

        // Act
        ResponseEntity<Void> response = productoController.eliminarProducto(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private Producto mockProducto () {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.CUENTA_AHORROS);
        producto.setSaldo(3000.00);
        producto.setExentaGMF(false);

        // Simular un cliente
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        producto.setCliente(cliente);
        return producto;
    }
}
