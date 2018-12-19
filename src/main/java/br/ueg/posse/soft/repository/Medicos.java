package br.ueg.posse.soft.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ueg.posse.soft.model.Medico;
import br.ueg.posse.soft.repository.helper.medico.MedicosQueries;

@Repository
public interface Medicos extends JpaRepository<Medico, Long>, MedicosQueries {
	
	public List<Medico> findByNomeContaining(String nome);
	
	public Optional<Medico>findByNomeIgnoreCase(String nome);

}
