package br.ueg.posse.soft.repository.helper.medico;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.ueg.posse.soft.model.Medico;
import br.ueg.posse.soft.repository.filter.EmpresaFilter;
import br.ueg.posse.soft.repository.filter.MedicoFilter;
import br.ueg.posse.soft.repository.paginacao.PaginacaoUtil;

public class MedicosImpl implements MedicosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<Medico> filtrar(MedicoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Medico.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(MedicoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Medico.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(MedicoFilter filtro, Criteria criteria) {
		if (filtro != null) {

						
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if (filtro.getCbo() != null) {

				criteria.add(Restrictions.eq("cbo", filtro.getCbo()));
			}

	
		}
	}

	public Page<Medico> filtrar(EmpresaFilter filtro, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
