package br.ueg.posse.soft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ueg.posse.soft.model.Usuario;
import br.ueg.posse.soft.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>,UsuariosQueries {

	public Optional<Usuario> findByNome(String nome);

}