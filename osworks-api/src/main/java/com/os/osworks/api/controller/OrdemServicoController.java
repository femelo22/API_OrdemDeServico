package com.os.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.os.osworks.api.model.OrdemServicoInput;
import com.os.osworks.api.model.OrdemServicoModel;
import com.os.osworks.domain.model.OrdemServico;
import com.os.osworks.domain.repository.OrdemServicoRepository;
import com.os.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")// Faz o mapeamento da nossa rota
public class OrdemServicoController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired // Injeta o repositório das OS
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = toEntity(ordemServicoInput);
		return toModel(gestaoOrdemServico.criar(ordemServico));
	}
	
	// Implementação das rota das listagens de OS
	@GetMapping
	public List<OrdemServicoModel> listar(){
		return toCollectionModel(ordemServicoRepository.findAll());// Da um get em todas as OS
	}
	
	// Implementação da rota GET por ID
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(ordemServicoId); // Busca no repositorio as OS por ID, retorna um Option de ordemServico
		
		if(ordemServico.isPresent()){ // Verifica se existe ordem de serviço
			OrdemServicoModel ordemServicoModel = toModel(ordemServico.get());
			
			return ResponseEntity.ok(ordemServicoModel); // Caso exista, retorna ok com o corpo da OS
		}
		
		return ResponseEntity.notFound().build();// Caso não encontre, retorne NOT FOUND
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.finalizar(ordemServicoId);
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class);
	}
	
	// Método para converter uma coleção de serviços em uma coleção de serviço model
	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream()
				.map(ordemServico -> toModel(ordemServico))
				.collect(Collectors.toList());
	}
	
	// Método para converter uma OSInput em uma OS
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
	
}
