package com.os.osworks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.os.osworks.api.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	
}
