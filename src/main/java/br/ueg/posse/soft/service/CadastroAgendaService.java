package br.ueg.posse.soft.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.posse.soft.model.Agenda;
import br.ueg.posse.soft.model.StatusAgenda;
import br.ueg.posse.soft.repository.Agendas;
import br.ueg.posse.soft.repository.filter.AgendaFilter;
import br.ueg.posse.soft.service.exception.HoraAgendaCadastradoException;
import br.ueg.posse.soft.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroAgendaService {

	@Autowired
	private Agendas agendas;

	@Transactional
	public void salvar(Agenda agenda) {

		Optional<Agenda> agendaOptional = agendas.findByHoraContaining(agenda
				.getHora());
		if (agendaOptional.isPresent()) {
			throw new HoraAgendaCadastradoException("Horario Não Disponivel");
		}

		agendas.save(agenda);

	}

	@Transactional
	public void excluir(Long codigo) {
		try {
			agendas.flush();
			agendas.delete(codigo);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossivel Apagar Medico Pois ja Esta Agendado!!");

		}
	}

	@Transactional
	public String receber(Long codigo) {
		Agenda agenda = agendas.findOne(codigo);
		agenda.setStatus(StatusAgenda.AGENDADO);
		agendas.save(agenda);

		return StatusAgenda.AGENDADO.getDescricao();
	}

	public List<Agenda> filtrar(AgendaFilter filtro) {
		String resultado = filtro.getResultado() == null ? "%" : filtro
				.getResultado();
		return agendas.findByResultadoContaining(resultado);

	}
}
