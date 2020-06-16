package com.os.osworks.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.os.osworks.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{

	List<Cliente> findByNome(String nome);// Tipo de query que realiza um busca por nome
	List<Cliente> findByNomeContaining(String nome);
	Cliente findByEmail(String email);// cria uma consulta por email
}
