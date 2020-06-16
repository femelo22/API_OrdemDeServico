package com.os.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.osworks.api.model.Comentario;
import com.os.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.os.osworks.domain.exception.NegocioException;
import com.os.osworks.domain.model.Cliente;
import com.os.osworks.domain.model.OrdemServico;
import com.os.osworks.domain.model.StatusOrdemServico;
import com.os.osworks.domain.repository.ClienteRepository;
import com.os.osworks.domain.repository.ComentarioRepository;
import com.os.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	@Autowired// Injetando o repositório da ordem de serviço
	private OrdemServicoRepository ordemServicoRepository; 
	
	@Autowired// Injetando o repositório da ordem de clientes
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {// Método que retorna a ordem de serviço
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())// busca o cliente pelo id do cliente que recebe a OS
				.orElseThrow(() -> new NegocioException("Cliente não encontrado."));
		
		ordemServico.setCliente(cliente);// Atribui o cliente a ordem de serviço
		ordemServico.setStatus(StatusOrdemServico.ABERTA);// Define o status inicial da ordem de serviço
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	// Método para finalizar a ordem de serviço
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}

	
	// Método que adiciona um comentário
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);// Aqui falamos para salvar o comentário
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
	
	
}
