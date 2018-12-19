package br.ueg.posse.soft.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.ueg.posse.soft.model.Usuario;
import br.ueg.posse.soft.repository.Usuarios;
import br.ueg.posse.soft.service.exception.NomeUsuarioJaCadastradoException;
import br.ueg.posse.soft.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarios.findByNome(usuario.getNome());
		if (usuarioExistente.isPresent()) {
			throw new NomeUsuarioJaCadastradoException(" já cadastrado");
		}
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if (usuario.isNovo()) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		usuarios.save(usuario);
	}
	
}
