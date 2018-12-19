package br.ueg.posse.soft.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.posse.soft.model.Medico;
import br.ueg.posse.soft.repository.Medicos;
import br.ueg.posse.soft.repository.filter.MedicoFilter;
import br.ueg.posse.soft.service.exception.ImpossivelExcluirEntidadeException;
import br.ueg.posse.soft.service.exception.MedicoAgendaCadastradoException;

@Service
public class CadastroMedicoService {

	@Autowired
	private Medicos medicos;

	@Transactional
	public void salvar(Medico medico) {
		Optional<Medico> medicoOptional = medicos.findByNomeIgnoreCase(medico
				.getNome());
		if (medicoOptional.isPresent()) {
			throw new MedicoAgendaCadastradoException("Esse Medico Já Esta Cadastrado");
		}

		medicos.save(medico);
	}

	@Transactional
	public void excluir(Long codigo) {
		try {
			medicos.flush();
			medicos.delete(codigo);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossivel Apagar Medico Pois Esta Agendado!!");

		}
	}

	public List<Medico> filtrar(MedicoFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return medicos.findByNomeContaining(nome);
	}

}
