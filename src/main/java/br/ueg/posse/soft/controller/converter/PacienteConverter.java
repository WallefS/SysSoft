package br.ueg.posse.soft.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.ueg.posse.soft.model.Paciente;

public class PacienteConverter implements Converter<String, Paciente> {

	@Override
	public Paciente convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Paciente paciente = new Paciente();
			paciente.setCodigo(Long.valueOf(codigo));
			return paciente;
		}

		return null;
	}

}
