package br.ueg.posse.soft.repository.filter;

public class AgendaFilter {

	private String nome;
	private String agenda;
	private Long id;
	private String resultado;
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAgenda() {
		return agenda;
	}

	public void setAgenda(String agenda) {
		this.agenda = agenda;
	}

}