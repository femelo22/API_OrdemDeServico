package com.os.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.osworks.domain.exception.NegocioException;
import com.os.osworks.domain.model.Cliente;
import com.os.osworks.domain.repository.ClienteRepository;

@Service//para a classe virar um componente do Spring, ou seja , esse componente fica visivel para ser ejetado dentro de outras classes como componente Spring, como por ex no clienteController
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		// Regra para não cadastrar o mesmo email mais de 1 vez.
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());// Pega do repositorio o email do cliente
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {// Verifica para garantir que é um cadastro ao invés de uma alteração 
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void exluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
}
