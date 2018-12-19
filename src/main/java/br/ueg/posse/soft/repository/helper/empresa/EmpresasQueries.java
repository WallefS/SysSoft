package br.ueg.posse.soft.repository.helper.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ueg.posse.soft.model.Empresa;
import br.ueg.posse.soft.repository.filter.EmpresaFilter;

public interface EmpresasQueries {
	
	public Page<Empresa> filtrar(EmpresaFilter filtro, Pageable pageable);


}
