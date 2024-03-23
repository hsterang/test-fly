package com.santer.testFly.clientes;

import com.santer.testFly.controller.ClienteController;
import com.santer.testFly.entity.Cliente;
import com.santer.testFly.repository.ClienteRepository;
import com.santer.testFly.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientesTest {

    private ClienteRepository clienteRepository = mock(ClienteRepository.class);
    private ClienteService clienteService = new ClienteService(clienteRepository);

    private ClienteController clienteController = new ClienteController(clienteService);

    @Test
    void crearCliente_DeberiaRetornarClienteCreado() throws ParseException {
        // Arrange
        Cliente cliente = mockClient();
        when(clienteRepository.save(any())).thenReturn(cliente);

        // Act
        ResponseEntity<Cliente> response = clienteController.crearCliente(cliente);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void actualizarCliente_DeberiaRetornarClienteActualizado() throws ParseException {
        // Arrange
        Long id = 1L;
        Cliente cliente = mockClient();
        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any())).thenReturn(cliente);

        // Act
        ResponseEntity<Cliente> response = clienteController.actualizarCliente(id, cliente);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void eliminarCliente_DeberiaRetornarRespuestaExitosa() {
        // Arrange
        Long id = 1L;
        doNothing().when(clienteRepository).deleteById(any());

        // Act
        ResponseEntity<Void> response = clienteController.eliminarCliente(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    private Cliente mockClient() throws ParseException {
        Cliente cliente = new Cliente();
        cliente.setApellidos("Perez");
        cliente.setNombres("Pepito");
        cliente.setCorreoElectronico("pepito@hotmail.com");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String fechaNacimientoString = "1998-05-15";

        Date fechaNacimiento = dateFormat.parse(fechaNacimientoString);
        cliente.setFechaNacimiento(fechaNacimiento);
        cliente.setNumeroIdentificacion("111111111");
        cliente.setTipoIdentificacion("CC");
        return cliente;
    }
}
