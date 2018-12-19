package br.ueg.posse.soft.service.exception;

public class NomeUsuarioJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NomeUsuarioJaCadastradoException(String message) {
		super(message);
	}

}
