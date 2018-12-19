package br.ueg.posse.soft.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ueg.posse.soft.model.Paciente;
import br.ueg.posse.soft.repository.helper.paciente.PacientesQueries;

@Repository
public interface Pacientes extends JpaRepository<Paciente, Long>,PacientesQueries{

	public List<Paciente> findByNomeContaining(String nome);
	
	public Optional<Paciente> findByNomeIgnoreCase(String nome);

	
}
