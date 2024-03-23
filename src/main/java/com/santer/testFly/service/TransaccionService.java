package com.santer.testFly.service;

import com.santer.testFly.entity.Producto;
import com.santer.testFly.entity.TipoTransaccion;
import com.santer.testFly.entity.Transaccion;
import com.santer.testFly.repository.ProductoRepository;
import com.santer.testFly.repository.TransaccionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;
    public TransaccionService(TransaccionRepository transaccionRepository, ProductoRepository productoRepository) {
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public void consignar(String numeroCuenta, double monto) {
        log.info("Realizando consignación en cuenta {} por un monto de {}", numeroCuenta, monto);
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto de la consignación debe ser mayor que cero.");
        }
        Optional<Producto> optionalProducto = productoRepository.findByNumeroCuenta(numeroCuenta);
        Producto producto = optionalProducto.orElseThrow(() -> new IllegalArgumentException("El producto con el número de cuenta especificado no existe."));

        double saldoActual = producto.getSaldo();
        double nuevoSaldo = saldoActual + monto;
        producto.setSaldo(nuevoSaldo);
        productoRepository.save(producto);

        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(TipoTransaccion.CONSIGNACION);
        transaccion.setCuentaOrigen(producto);
        transaccion.setMonto(monto);
        transaccionRepository.save(transaccion);
        log.info("Consignación realizada exitosamente");
    }

    @Transactional
    public void retirar(String numeroCuenta, double monto) {
        log.info("Realizando retiro desde cuenta {} por un monto de {}", numeroCuenta, monto);
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del retiro debe ser mayor que cero.");
        }
        Optional<Producto> optionalProducto = productoRepository.findByNumeroCuenta(numeroCuenta);

        Producto producto = optionalProducto.orElseThrow(() -> new IllegalArgumentException("El producto con el número de cuenta especificado no existe."));

        double saldoActual = producto.getSaldo();
        if (saldoActual < monto) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro.");
        }

        double nuevoSaldo = saldoActual - monto;
        producto.setSaldo(nuevoSaldo);
        productoRepository.save(producto);

        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(TipoTransaccion.RETIRO);
        transaccion.setCuentaOrigen(producto);
        transaccion.setMonto(monto);
        transaccionRepository.save(transaccion);
        log.info("Retiro realizado exitosamente");
    }

    @Transactional
    public void transferir(String numeroCuentaOrigen, String numeroCuentaDestino, double monto) {
        log.info("Realizando transferencia desde cuenta {} a cuenta {} por un monto de {}", numeroCuentaOrigen, numeroCuentaDestino, monto);
        if (numeroCuentaOrigen.equalsIgnoreCase(numeroCuentaDestino)) {
            throw new IllegalArgumentException("La cuenta de destino es la misma que la cuenta de origen.");
        }
        Optional<Producto> optionalProductoOrigen = productoRepository.findByNumeroCuenta(numeroCuentaOrigen);
        Optional<Producto> optionalProductoDestino = productoRepository.findByNumeroCuenta(numeroCuentaDestino);

        Producto productoOrigen = optionalProductoOrigen.orElseThrow(() -> new IllegalArgumentException("El producto de origen con el número de cuenta especificado no existe."));
        Producto productoDestino = optionalProductoDestino.orElseThrow(() -> new IllegalArgumentException("El producto de destino con el número de cuenta especificado no existe."));

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto de la transferencia debe ser mayor que cero.");
        }

        double saldoOrigenActual = productoOrigen.getSaldo();
        if (saldoOrigenActual < monto) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta de origen para realizar la transferencia.");
        }

        double nuevoSaldoOrigen = saldoOrigenActual - monto;
        double nuevoSaldoDestino = productoDestino.getSaldo() + monto;
        productoOrigen.setSaldo(nuevoSaldoOrigen);
        productoDestino.setSaldo(nuevoSaldoDestino);
        productoRepository.save(productoOrigen);
        productoRepository.save(productoDestino);

        Transaccion transaccionOrigen = new Transaccion();
        transaccionOrigen.setTipo(TipoTransaccion.TRANSFERENCIA);
        transaccionOrigen.setCuentaOrigen(productoOrigen);
        transaccionOrigen.setCuentaDestino(productoDestino);
        transaccionOrigen.setMonto(-monto);
        transaccionOrigen.setFecha(new Date());
        transaccionRepository.save(transaccionOrigen);
        log.info("Transferencia realizada exitosamente");
    }
}