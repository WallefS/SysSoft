package br.ueg.posse.soft.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ueg.posse.soft.model.Cidade;
import br.ueg.posse.soft.model.Estado;
import br.ueg.posse.soft.repository.helper.cidade.CidadesQueries;


public interface Cidades extends JpaRepository<Cidade, Long>,CidadesQueries  {
	
	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);



}
