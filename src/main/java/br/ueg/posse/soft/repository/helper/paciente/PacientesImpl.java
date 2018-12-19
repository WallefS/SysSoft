package br.ueg.posse.soft.repository.helper.paciente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.ueg.posse.soft.model.Paciente;
import br.ueg.posse.soft.repository.filter.PacienteFilter;
import br.ueg.posse.soft.repository.paginacao.PaginacaoUtil;

public class PacientesImpl implements PacientesQueries{

	
	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Paciente> filtrar(PacienteFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Paciente.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(PacienteFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Paciente.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
		private void adicionarFiltro(PacienteFilter filtro, Criteria criteria) {
			if (filtro != null) {

							
				if (!StringUtils.isEmpty(filtro.getNome())) {
					criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
				}

				if (filtro.getNumero() != null) {

					criteria.add(Restrictions.eq("numero", filtro.getNumero()));
				}
				

		
			}
		}
}
