package com.santer.testFly.service;

import com.santer.testFly.entity.Cliente;
import com.santer.testFly.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.santer.testFly.utils.ValidationUtils.validarEdad;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> obtenerClientePorId(String id) {
        return clienteRepository.findByNumeroIdentificacion(id);
    }

    public Cliente crearCliente(Cliente cliente) {
        validarEdad(cliente.getFechaNacimiento());
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            cliente.setId(id);
            validarEdad(cliente.getFechaNacimiento());
            return clienteRepository.save(cliente);
        } else {
            return null;
        }
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}