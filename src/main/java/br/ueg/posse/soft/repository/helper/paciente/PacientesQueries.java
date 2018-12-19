package br.ueg.posse.soft.repository.helper.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ueg.posse.soft.model.Paciente;
import br.ueg.posse.soft.repository.filter.PacienteFilter;

public interface PacientesQueries {

	public Page<Paciente> filtrar(PacienteFilter filtro,Pageable pageable);
}
