package br.ueg.posse.soft.repository.helper.exame;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ueg.posse.soft.model.Exame;
import br.ueg.posse.soft.repository.filter.ExameFilter;

public interface ExamesQueries {
	
	public Page<Exame>filtrar(ExameFilter filtro, Pageable pageable);

}
