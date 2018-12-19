package br.ueg.posse.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.posse.soft.model.Empresa;
import br.ueg.posse.soft.repository.Empresas;
import br.ueg.posse.soft.repository.filter.EmpresaFilter;

@Service
public class CadastroEmpresaService {

	@Autowired
	private Empresas empresas;

	@Transactional
	public void salvar(Empresa empresa) {

		try {
			empresas.save(empresa);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data Invalido");
		}
	}

	@Transactional
	public void excluir(Empresa empresa) {
		empresas.delete(empresa);
	}

	public List<Empresa> filtrar(EmpresaFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return empresas.findByNomeContaining(nome);
	}

}
