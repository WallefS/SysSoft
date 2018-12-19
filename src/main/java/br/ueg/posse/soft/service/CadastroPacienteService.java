package br.ueg.posse.soft.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.posse.soft.model.Paciente;
import br.ueg.posse.soft.repository.Pacientes;
import br.ueg.posse.soft.repository.filter.PacienteFilter;
import br.ueg.posse.soft.service.exception.PacienteAgendaCadastradoException;

@Service
public class CadastroPacienteService {

	@Autowired
	private Pacientes pacientes;

	@Transactional
	public void salvar(Paciente paciente) {

		Optional<Paciente> pacienteOptional = pacientes
				.findByNomeIgnoreCase(paciente.getNome());
		if (pacienteOptional.isPresent()) {
			throw new PacienteAgendaCadastradoException(
					"Paciente Já Cadastrado Tente Outro!!");
		}

		pacientes.save(paciente);
	}

	@Transactional
	public void excluir(Paciente paciente) {
		pacientes.delete(paciente);
	}

	public List<Paciente> filtrar(PacienteFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return pacientes.findByNomeContaining(nome);

	}
}
