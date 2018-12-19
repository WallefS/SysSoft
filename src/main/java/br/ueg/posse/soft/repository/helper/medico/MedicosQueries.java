package br.ueg.posse.soft.repository.helper.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.ueg.posse.soft.model.Medico;
import br.ueg.posse.soft.repository.filter.MedicoFilter;

public interface MedicosQueries {
	
	public Page<Medico>filtrar(MedicoFilter filtro, Pageable pageable);

}
