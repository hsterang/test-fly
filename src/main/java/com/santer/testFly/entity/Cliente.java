package com.santer.testFly.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", unique = true)
    private String numeroIdentificacion;

    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    @Column(name = "nombres")
    private String nombres;

    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "correo_electronico")
    @Email(message = "Email deberia ser valido")
    private String correoElectronico;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = new Date();
        fechaModificacion = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = new Date();
    }
}