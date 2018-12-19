package br.ueg.posse.soft.repository.helper.exame;

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

import br.ueg.posse.soft.model.Exame;
import br.ueg.posse.soft.repository.filter.ExameFilter;
import br.ueg.posse.soft.repository.paginacao.PaginacaoUtil;

public class ExamesImpl implements ExamesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Exame> filtrar(ExameFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(
				Exame.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));

	}

	private Long total(ExameFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(
				Exame.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ExameFilter filtro, Criteria criteria) {
		if (filtro != null) {

			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(),
						MatchMode.ANYWHERE));
			}

			if (filtro.getNome() != null) {

				criteria.add(Restrictions.eq("nome", filtro.getNome()));
			}

		}
	}

}
