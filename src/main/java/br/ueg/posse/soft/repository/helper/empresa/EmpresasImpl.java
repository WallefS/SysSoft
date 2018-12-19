package br.ueg.posse.soft.repository.helper.empresa;

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

import br.ueg.posse.soft.model.Empresa;
import br.ueg.posse.soft.repository.filter.EmpresaFilter;
import br.ueg.posse.soft.repository.paginacao.PaginacaoUtil;

public class EmpresasImpl implements EmpresasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Empresa> filtrar(EmpresaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Empresa.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(EmpresaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Empresa.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(EmpresaFilter filtro, Criteria criteria) {
		if (filtro != null) {

						
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if (filtro.getCnpj() != null) {

				criteria.add(Restrictions.eq("cnpj", filtro.getCnpj()));
			}

	
		}
	}

}
