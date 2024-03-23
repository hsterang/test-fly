package com.santer.testFly.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ValidationUtils {
    private ValidationUtils() {
        throw new IllegalArgumentException("Error en la creción");
    }

    public static void validarEdad (Date fechaNacimientoCliente) {
        LocalDate fechaLimite = LocalDate.now().minusYears(18);
        LocalDate fechaNacimiento = fechaNacimientoCliente.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        if (fechaNacimiento.isAfter(fechaLimite)) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad para ser registrado.");
        }
    }
}
