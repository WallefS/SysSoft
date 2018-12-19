package br.ueg.posse.soft.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import br.ueg.posse.soft.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> nome(String nome);
	
	public List<String> permissoes(Usuario usuario);
	
}