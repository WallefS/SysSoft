package br.ueg.posse.soft.repository.helper.agenda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ueg.posse.soft.model.Agenda;
import br.ueg.posse.soft.repository.filter.AgendaFilter;

public interface AgendasQueries {
	
	public Page<Agenda> filtrar(AgendaFilter filtro, Pageable pageable);

}
