package com.santer.testFly.service;

import com.santer.testFly.entity.Cliente;
import com.santer.testFly.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.santer.testFly.utils.ValidationUtils.validarEdad;

@Service
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> obtenerClientePorId(String id) {
        log.info("Obteniendo cliente por ID: {}", id);
        Optional<Cliente> cliente = clienteRepository.findByNumeroIdentificacion(id);
        if (cliente.isPresent()) {
            log.info("Cliente encontrado por ID: {}", id);
        } else {
            log.warn("No se encontró cliente con ID: {}", id);
        }
        return cliente;
    }

    public Cliente crearCliente(Cliente cliente) {
        log.info("Creando nuevo cliente: {}", cliente);
        validarEdad(cliente.getFechaNacimiento());
        Cliente nuevoCliente = clienteRepository.save(cliente);
        log.info("Cliente creado: {}", nuevoCliente);
        return nuevoCliente;
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        log.info("Actualizando cliente con ID: {}", id);
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            cliente.setId(id);
            validarEdad(cliente.getFechaNacimiento());
            Cliente clienteActualizado = clienteRepository.save(cliente);
            log.info("Cliente actualizado: {}", clienteActualizado);
            return clienteActualizado;
        } else {
            log.warn("No se encontró cliente para actualizar con ID: {}", id);
            return null;
        }
    }

    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado con ID: {}", id);
    }
}