package org.Generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.Generation.blogPessoal.model.User;
import org.Generation.blogPessoal.model.UserLogin;
import org.Generation.blogPessoal.repository.UsuarioRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public Optional<User> cadastrarUsuario(User usuario) {

		if (repository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe.", null);

		usuario.setSenha(criptografaSenha(usuario.getSenha()));
		return Optional.of(repository.save(usuario));
	}

	public Optional<User> atualizarUsuario(User usuario) {

		if (repository.findById(usuario.getId()).isPresent()) {
			Optional<User> buscaUsuario = repository.findByUsuario(usuario.getUsuario());

			if (buscaUsuario.isPresent()) {
				if (buscaUsuario.get().getId() != usuario.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe.", null);
			}
			usuario.setSenha(criptografaSenha(usuario.getSenha()));
			return Optional.of(repository.save(usuario));
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.", null);
	}

	public Optional<UserLogin> logar(Optional<UserLogin> usuarioLogin) {

		Optional<User> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());
		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getPassword(), usuario.get().getSenha())) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setName(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setTipo(usuario.get().getTipo());
				usuarioLogin.get().setToken(generatorBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getPassword()));
                usuarioLogin.get().setPassword(usuario.get().getSenha());

				return usuarioLogin;
			}
		}
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos.", null);
	}

	private String criptografaSenha(String senha) {
		String senhaEncoder = encoder.encode(senha);
		return senhaEncoder;
	}

	private boolean compararSenhas(String senhaDigitada, String senhaDB) {
		return encoder.matches(senhaDigitada, senhaDB);
	}

	private String generatorBasicToken(String email, String password) {
		String structure = email + ":" + password;
		byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(structureBase64);
	}
}