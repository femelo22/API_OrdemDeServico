package com.os.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.os.osworks.domain.model.Cliente;
import com.os.osworks.domain.repository.ClienteRepository;
import com.os.osworks.domain.service.CadastroClienteService;

@RestController// Define a classe do controlador Get
@RequestMapping("/clientes")// Define que esse controlador responde tudo que estiver em /clientes
public class ClienteController {
		
	@Autowired // Diz que a gnt precisa de uma instancia de cliente nesse local
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService cadastroCliente;
	
	@GetMapping // ROTA GET --> lista de clientes
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) { // ResponseEntity ajuda a manipular melhor as respostas das consultas
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		// Verificação para ver se achou o cliente
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();// Retorna 404 Not Found caso não ache na consulta
	}
	
	// Método de CADASTRO de Cliente
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)// Faz com que a resposta da requisição post retorne um status 201(Created)
	public 	Cliente adicionar(@Valid @RequestBody Cliente cliente) {// Diz pro spring trasformar o que vem no corpo da requisição em objeto cliente    Valid ATIVA a validação do Bean Validate
		return cadastroCliente.salvar(cliente);// Salva a partir da classe criada como Regra de Negocio
	}
	
	// Endpoint de Atualização
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, 
			@RequestBody Cliente cliente){
		
		if(!clienteRepository.existsById(clienteId)) {// Verifica se existe o ID
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);// Atribuimos o id a instância de cliente para dizermos que queremos atualizar, não criar um novo
		cliente = cadastroCliente.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	// Entry Point para Exclusão
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)) {// Verifica se existe o ID
			return ResponseEntity.notFound().build();
		}
		
		cadastroCliente.exluir(clienteId);
		
		return ResponseEntity.noContent().build(); // noContent é o codigo 204, não encontrado
	}
	
}
