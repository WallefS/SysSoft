package br.ueg.posse.soft.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import br.ueg.posse.soft.validation.CBO;

@Entity
@Table(name = "medico")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "codigo")
	private long codigo;

	@Basic(optional = false)
	@NotEmpty(message = "Nome é obrigatório")
	@Size(min = 1, max = 60)
	@Column(name = "nome")
	private String nome;

	@CBO
	@Basic(optional = false)
	@NotNull(message = "Cbo é obrigatório")
	@Size(min = 1, max = 6, message = " Cbo Inválido")
	@Column(name = "cbo")
	private String cbo;

	@CPF
	private String cpf;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "medico")
	private List<Agenda> agendalist;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Agenda> getAgendalist() {
		return agendalist;
	}

	public void setAgendalist(List<Agenda> agendalist) {
		this.agendalist = agendalist;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCbo() {
		return cbo;
	}

	public void setCbo(String cbo) {
		this.cbo = cbo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codigo ^ (codigo >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medico other = (Medico) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return " " + nome + "";

	}
}
