package br.ueg.posse.soft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ueg.posse.soft.model.Exame;
import br.ueg.posse.soft.repository.helper.exame.ExamesQueries;

@Repository
public interface Exames extends JpaRepository<Exame, Long>,ExamesQueries {
	
	public List<Exame> findByNomeContaining(String nome);

	

}
