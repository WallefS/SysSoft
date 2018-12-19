package br.ueg.posse.soft.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.ueg.posse.soft.model.Exame;

public class ExameConverter implements Converter<String, Exame> {

	@Override
	public Exame convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Exame exame = new Exame();
			exame.setCodigo(Long.valueOf(codigo));
			return exame;
		}

		return null;
	}

}
