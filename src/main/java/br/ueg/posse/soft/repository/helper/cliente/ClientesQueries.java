package br.ueg.posse.soft.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ueg.posse.soft.model.Cliente;
import br.ueg.posse.soft.repository.filter.ClienteFilter;

public interface ClientesQueries {

	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);
	
}
