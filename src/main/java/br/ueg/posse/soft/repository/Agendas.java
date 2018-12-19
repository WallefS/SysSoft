package br.ueg.posse.soft.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ueg.posse.soft.model.Agenda;
import br.ueg.posse.soft.repository.helper.agenda.AgendasQueries;

@Repository
public interface Agendas extends JpaRepository<Agenda, Long>,AgendasQueries {

	 //public List<Agenda> findByIdContaining(String id);

	public List<Agenda> findByResultadoContaining(String resultado);

	public Optional<Agenda>findByHoraContaining(String hora);
	
	
	
	

	

}
