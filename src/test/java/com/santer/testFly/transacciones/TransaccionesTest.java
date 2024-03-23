package com.santer.testFly.transacciones;

import com.santer.testFly.controller.ProductoController;
import com.santer.testFly.controller.TransaccionController;
import com.santer.testFly.entity.Cliente;
import com.santer.testFly.entity.Producto;
import com.santer.testFly.entity.TipoCuenta;
import com.santer.testFly.entity.Transaccion;
import com.santer.testFly.entity.TransferenciaRequest;
import com.santer.testFly.repository.ProductoRepository;
import com.santer.testFly.repository.TransaccionRepository;
import com.santer.testFly.service.ProductoService;
import com.santer.testFly.service.TransaccionService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class TransaccionesTest {
    private TransaccionRepository transaccionRepository = mock(TransaccionRepository.class);
    private ProductoRepository productoRepository = mock(ProductoRepository.class);

    private TransaccionService transaccionService = new TransaccionService(transaccionRepository, productoRepository);
    private TransaccionController transaccionController = new TransaccionController(transaccionService);

    @Test
    void testRealizarConsignacion() {
        // Arrange
        Producto cuenta = mockProductoClient1();
        cuenta.setSaldo(500.0);
        Transaccion consignacion = new Transaccion();
        when(productoRepository.findByNumeroCuenta(any())).thenReturn(Optional.of(cuenta));
        when(transaccionRepository.save(consignacion)).thenReturn(consignacion);

        // Act
        transaccionController.consignar(mockRequest("5344498789", "", 1000));

        // Assert
        verify(productoRepository, times(1)).save(any());
        verify(transaccionRepository, times(1)).save(any());

    }

    @Test
    void testRealizarRetiro() {
        // Arrange
        Producto cuenta = mockProductoClient1();
        Transaccion consignacion = new Transaccion();
        when(productoRepository.findByNumeroCuenta(any())).thenReturn(Optional.of(cuenta));
        when(transaccionRepository.save(consignacion)).thenReturn(consignacion);

        // Act
        transaccionController.retirar(mockRequest("5344498789", "", 1000));

        // Assert
        verify(productoRepository, times(1)).save(any());
        verify(transaccionRepository, times(1)).save(any());
    }

    @Test
    void testRealizarTransaccion() {
        // Arrange
        Producto cuenta = mockProductoClient1();
        Producto cuenta2 = mockProductoClient2();
        Transaccion consignacion = new Transaccion();
        when(productoRepository.findByNumeroCuenta("5344498789")).thenReturn(Optional.of(cuenta));
        when(productoRepository.findByNumeroCuenta("5344498787")).thenReturn(Optional.of(cuenta2));
        when(transaccionRepository.save(consignacion)).thenReturn(consignacion);

        // Act
        transaccionController.transferir(mockRequest("5344498789", "5344498787", 1000));

        // Assert
        verify(productoRepository, times(2)).save(any());
        verify(transaccionRepository, times(1)).save(any());

    }

    private Producto mockProductoClient1 () {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.CUENTA_AHORROS);
        producto.setSaldo(3000.00);
        producto.setExentaGMF(false);
        producto.setNumeroCuenta("5344498789");
        // Simular un cliente
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        producto.setCliente(cliente);
        return producto;
    }
    private Producto mockProductoClient2 () {
        Producto producto = new Producto();
        producto.setTipoCuenta(TipoCuenta.CUENTA_AHORROS);
        producto.setSaldo(3000.00);
        producto.setExentaGMF(false);
        producto.setNumeroCuenta("5344498787");
        // Simular un cliente
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        producto.setCliente(cliente);
        return producto;
    }

    private TransferenciaRequest mockRequest (String cuentaOrigen, String cuentaDestino, double monto) {
        return TransferenciaRequest
                .builder()
                .numeroCuentaOrigen(cuentaOrigen)
                .numeroCuentaDestino(cuentaDestino)
                .monto(monto)
                .build();
    }
}
