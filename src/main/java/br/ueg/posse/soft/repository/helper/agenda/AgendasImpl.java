package br.ueg.posse.soft.repository.helper.agenda;

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

import br.ueg.posse.soft.model.Agenda;
import br.ueg.posse.soft.repository.filter.AgendaFilter;
import br.ueg.posse.soft.repository.paginacao.PaginacaoUtil;

public class AgendasImpl implements AgendasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Agenda> filtrar(AgendaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(
				Agenda.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(AgendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(
				Agenda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();

	}

	private void adicionarFiltro(AgendaFilter filtro, Criteria criteria) {
		if (filtro != null) {

			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(),
						MatchMode.ANYWHERE));
			}

			if (filtro.getAgenda() != null) {

				criteria.add(Restrictions.eq("agenda", filtro.getAgenda()));
			}

		}
	}

}
