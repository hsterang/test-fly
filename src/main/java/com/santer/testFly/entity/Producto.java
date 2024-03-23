package com.santer.testFly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
import java.util.Random;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TipoCuenta tipoCuenta;

    @Column(name = "numero_cuenta", unique = true)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCuenta estado;

    @Column(name = "saldo")
    private double saldo;

    @Column(name = "exenta_gmf")
    private boolean exentaGMF;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @PrePersist
    public void onCreate() {
        // Generar el prefijo según el tipo de cuenta
        String prefijo = "";
        if (tipoCuenta == TipoCuenta.CUENTA_CORRIENTE) {
            prefijo = "33";
        } else if (tipoCuenta == TipoCuenta.CUENTA_AHORROS) {
            prefijo = "53";
        }
        Random random = new Random();
        // Generar el número aleatorio de 8 dígitos
        String numeroAleatorio = String.format("%08d", random.nextInt(100000000));

        // Concatenar el prefijo y el número aleatorio para formar el número de cuenta
        numeroCuenta = prefijo + numeroAleatorio;
        fechaCreacion = new Date();
        fechaModificacion = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = new Date();
    }

}