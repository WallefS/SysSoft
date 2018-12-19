package br.ueg.posse.soft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "agenda")
public class Agenda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull(message = "Medico é obrigatório")
	@JoinColumn
	@ManyToOne(optional = false)
	private Medico medico;

	@NotNull(message = "Exame é obrigatório")
	@JoinColumn
	@ManyToOne(optional = false)
	private Exame exame;

	@NotNull(message = "Paciente é obrigatório")
	@JoinColumn
	@ManyToOne(optional = false)
	private Paciente paciente;

	@Size(max = 655)
	@Column(name = "resultado")
	private String resultado;

	@Future(message = "Data Não disponivel")
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(value = TemporalType.DATE)
	private Date data;

	@NotNull
	@Column(name = "hora")
	private String hora;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusAgenda status;

	public StatusAgenda getStatus() {
		return status;
	}

	public void setStatus(StatusAgenda status) {
		this.status = status;
	}

	public boolean isPendente() {
		return StatusAgenda.PENDENTE.equals(this.status);
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
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
		Agenda other = (Agenda) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
