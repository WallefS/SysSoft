package br.ueg.posse.soft.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(name = "nome")
	@Size(min = 1, max = 60)
	private String nome;

	@Past(message = "Data precisa ser menor ou igual que a data atual")
	@Column(name = "data_Nasc")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Data de nascimento é obrigatória")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataNasc;

	@Embedded
	private Endereco endereco;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
	private List<Agenda> agendalist;

	public Paciente() {
	}

	public Paciente(long codigo) {
		this.codigo = codigo;
	}

	public Paciente(long id, String nome, Date dataNasc, Long codigo) {
		this.codigo = codigo;
		this.nome = nome;
		this.dataNasc = dataNasc;

	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Agenda> getAgendalist() {
		return agendalist;
	}

	public void setAgendalist(List<Agenda> agendalist) {
		this.agendalist = agendalist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Paciente other = (Paciente) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return " " + nome + " ";

	}
}
