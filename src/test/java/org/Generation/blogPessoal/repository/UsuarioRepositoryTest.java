package org.Generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.Generation.blogPessoal.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		/// GIVEN -> Dado, que tenho 4 usuarios criados no banco de dados.
		usuarioRepository.save(new User(0L,"luciana boaz", "https://www.instagram.com/p/BjnQnGiFpsb/", "luciana@email.com", "luci1234"));
		usuarioRepository.save(new User(0L,"caique boaz", "https://pps.whatsapp.net/v/t61.24694-24/160395022_505009317640388_7743331428513349132_n.jpg?ccb=11-4&oh=01_AVyY7de5fmptjNOR34aAOf1snYB6PfALBqL4HEQnAcWebQ&oe=623597CF", "caique@email", "caicao1234"));			
		usuarioRepository.save(new User(0L,"gustavo boaz","https://media.discordapp.net/attachments/941783600704147496/952008523473105037/142846838_1367681533582620_8444293250628177862_n.jpg?width=315&height=472", "boaz@email", "boaz123"));
		usuarioRepository.save(new User(0L,"marcelo", "https://pps.whatsapp.net/v/t61.24694-24/184536407_339593677650857_1485956363971890438_n.jpg?ccb=11-4&oh=01_AVz6PaBON8Dmd9ObejOndtoRSwTFnrp-mcAuLVzbxmyqcg&oe=62351757", "marcelo@email", "marcelao1234"));			
	}
	
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		/// WHEN -> Quando, eu pesquiso pelo usuario com email luciana@email.com
		Optional<User> usuario = usuarioRepository.findByUsuario("luciana@email.com");
		
		/// THEN -> Então, devo receber o usuario da luciana@email.com
		assertTrue(usuario.get().getUsuario().equals("luciana@email.com")); 
	}
	
	@Test
	@DisplayName("Retorna 3 usuario")
	public void deveRetornarTresUsuarios() {
		/// WHEN -> Quando, quando eu faço uma pesquisa por um nome que contenha boaz
		List<User> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("boaz");
		
		/// THEN -> Então, devo receber lista com 3 usuario
		assertEquals(3, listaDeUsuarios.size()); ///(esperado, atual)
		
		// THEN -> Então, o usuario no indice 0 é igual a luciana boaz
		assertTrue(listaDeUsuarios.get(0).getNome().equals("luciana boaz"));
		
		// THEN -> Então, o usuario no indice 1 é igual a caique boaz
		assertTrue(listaDeUsuarios.get(1).getNome().equals("caique boaz"));
		
		// THEN -> Então, o usuario no indice 2 é igual a gustavo boaz
		assertTrue(listaDeUsuarios.get(2).getNome().equals("gustavo boaz"));

	}
}
