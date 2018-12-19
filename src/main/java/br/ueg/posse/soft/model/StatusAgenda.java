package br.ueg.posse.soft.model;

public enum StatusAgenda {
	
	PENDENTE("Pendente"),
	AGENDADO("Agendado");
	
	private String descricao;
	
	StatusAgenda(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	


}
