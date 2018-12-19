package br.ueg.posse.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.posse.soft.model.Exame;
import br.ueg.posse.soft.repository.Exames;
import br.ueg.posse.soft.repository.filter.ExameFilter;

@Service
public class CadastroExameService {

	@Autowired
	private Exames exames;

	@Transactional
	public void salvar(Exame exame) {

		try {
			exames.save(exame);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data Invalido");
		}

	}

	@Transactional
	public void excluir(Long codigo) {
		exames.flush();
		exames.delete(codigo);
	}

	public List<Exame> filtrar(ExameFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return exames.findByNomeContaining(nome);

	}
}
