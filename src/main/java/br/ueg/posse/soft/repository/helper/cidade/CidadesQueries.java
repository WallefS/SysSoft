package br.ueg.posse.soft.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ueg.posse.soft.model.Cidade;
import br.ueg.posse.soft.repository.filter.CidadeFilter;

public interface CidadesQueries {

	public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable);
	
}