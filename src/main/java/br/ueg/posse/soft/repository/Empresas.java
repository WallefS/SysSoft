package br.ueg.posse.soft.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ueg.posse.soft.model.Empresa;
import br.ueg.posse.soft.repository.helper.empresa.EmpresasQueries;

@Repository
public interface Empresas extends JpaRepository<Empresa, Long>, EmpresasQueries{
	
	public List<Empresa> findByNomeContaining(String nome);

}
