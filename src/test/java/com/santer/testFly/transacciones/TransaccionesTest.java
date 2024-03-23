package com.santer.testFly.transacciones;

import com.santer.testFly.controller.TransaccionController;
import com.santer.testFly.entity.Cliente;
import com.santer.testFly.entity.Producto;
import com.santer.testFly.entity.TipoCuenta;
import com.santer.testFly.entity.Transaccion;
import com.santer.testFly.entity.TransferenciaRequest;
import com.santer.testFly.repository.ProductoRepository;
import com.santer.testFly.repository.TransaccionRepository;
import com.santer.testFly.service.TransaccionService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
class TransaccionesTest {
    private final TransaccionRepository transaccionRepository = mock(TransaccionRepository.class);
    private final ProductoRepository productoRepository = mock(ProductoRepository.class);
    private final TransaccionService transaccionService = new TransaccionService(transaccionRepository, productoRepository);
    private final TransaccionController transaccionController = new TransaccionController(transaccionService);

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
    void testRealizarConsignacionMontoEmpty() {
        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionController.consignar(mockRequest("5344498789", "", 0));
        });
    }
    @Test
    void testRealizarConsignacionSinSaldo() {
        // Arrange
        Producto cuenta = mockProductoClient1();
        cuenta.setSaldo(0);
        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionController.consignar(mockRequest("5344498789", "", 1000));
        });

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
    void testRealizarRetiroMontoEmpty() {
        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionController.retirar(mockRequest("5344498789", "", 0));
        });
    }
    @Test
    void testRealizarRetiroSaldoInsuficiente() {
        // Arrange
        Producto cuenta = mockProductoClient1();
        cuenta.setSaldo(0);
        Transaccion consignacion = new Transaccion();
        when(productoRepository.findByNumeroCuenta(any())).thenReturn(Optional.of(cuenta));
        when(transaccionRepository.save(consignacion)).thenReturn(consignacion);

        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionController.retirar(mockRequest("5344498789", "", 1000));
        });
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

    @Test
    void testRealizarTransaccionSinSaldo() {
        // Arrange
        Producto cuenta = mockProductoClient1();
        cuenta.setSaldo(0);
        Producto cuenta2 = mockProductoClient2();
        Transaccion consignacion = new Transaccion();
        when(productoRepository.findByNumeroCuenta("5344498789")).thenReturn(Optional.of(cuenta));
        when(productoRepository.findByNumeroCuenta("5344498787")).thenReturn(Optional.of(cuenta2));
        when(transaccionRepository.save(consignacion)).thenReturn(consignacion);

        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionController.transferir(mockRequest("5344498789", "5344498787", 1000));
        });

    }

    @Test
    void testRealizarTransaccionCuentaNoExiste() {
        // Arrange
        Producto cuenta2 = mockProductoClient2();
        Transaccion consignacion = new Transaccion();
        when(productoRepository.findByNumeroCuenta("5344498789")).thenReturn(Optional.empty());
        when(productoRepository.findByNumeroCuenta("5344498787")).thenReturn(Optional.of(cuenta2));
        when(transaccionRepository.save(consignacion)).thenReturn(consignacion);

        // Act
        assertThrows(IllegalArgumentException.class, () -> {

            transaccionController.transferir(mockRequest("5344498789", "5344498787", 1000));
        });

    }

    @Test
    void testRealizarTransferirMismasCuentas() {
        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionController.transferir(mockRequest("5344498789", "5344498789", 0));
        });
    }

    @Test
    void testRealizarTransaccionMontoCero() {
        // Arrange
        Producto cuenta = mockProductoClient1();
        cuenta.setSaldo(0);
        Producto cuenta2 = mockProductoClient2();
        Transaccion consignacion = new Transaccion();
        when(productoRepository.findByNumeroCuenta("5344498789")).thenReturn(Optional.of(cuenta));
        when(productoRepository.findByNumeroCuenta("5344498787")).thenReturn(Optional.of(cuenta2));
        when(transaccionRepository.save(consignacion)).thenReturn(consignacion);

        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionController.transferir(mockRequest("5344498789", "5344498787", 0));
        });

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
