package br.com.springboot.curso_jdev_treinamento_dsfb.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento_dsfb.model.Usuario;
import br.com.springboot.curso_jdev_treinamento_dsfb.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository; // CI, CD, CDI, inversão de dependências!!!
	
    @GetMapping(value="listaTodos") /* Nosso primeiro método de API Rest! */
   @ResponseBody /* dados para o corpo da resposta */
   public ResponseEntity<List<Usuario>> listaUsuarios() {
	   List<Usuario> usuarios = this.usuarioRepository.findAll();
	   return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
   }

   @PostMapping(value="salvar") /* Mapeia a URL */
   @ResponseBody /* descreve a resposta */
   public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { /* recebe os dados p/ salvar. */
	   Usuario novoUsuario = this.usuarioRepository.save(usuario);
	   return new ResponseEntity<Usuario>(novoUsuario, HttpStatus.CREATED);
   }

   @DeleteMapping(value="apagar") /* Mapeia a URL */
   @ResponseBody /* descreve a resposta */
   public ResponseEntity<String> apagar(@RequestParam Long idUser) {
	   try {
		   this.usuarioRepository.deleteById(idUser);
		   return new ResponseEntity<String>("Usuário apagado com sucesso!", HttpStatus.OK);
	   } catch (EmptyResultDataAccessException e) {
		   return new ResponseEntity<String>("O usuário informado não existe!", HttpStatus.BAD_REQUEST);
	   }
   }

   @GetMapping(value="buscarUserId") /* Mapeia a URL */
   @ResponseBody /* descreve a resposta */
   public ResponseEntity<?> buscarUserId(@RequestParam(name = "idUser") Long idUser) {
	   try {
		   Usuario usuario = usuarioRepository.findById(idUser).get();
		   return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	   } catch (NoSuchElementException e) {
		   return new ResponseEntity<String>("O id informado não é de um usuário existente!", HttpStatus.BAD_REQUEST);
	   }
   }
   
   @PutMapping(value="atualizar") /* Mapeia a URL */
   @ResponseBody // descreve a resposta
   public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
	   /* Vai retornar uma ResponseEntity genérica para lidar com casos
	    * de sucesso ou de falhas!
	    * Recebe dados para update!
	    * */
	   if (usuario.getId() == null) {
		   return new ResponseEntity<String>("O id não foi informado p/ atualizar o usuário!", HttpStatus.BAD_REQUEST);
	   }

	   Usuario user = usuarioRepository.saveAndFlush(usuario);
	   return new ResponseEntity<Usuario>(user, HttpStatus.OK);
   }

   @GetMapping(value = "buscarPorNome") /* Mapeia a URL */
   @ResponseBody /* descreve a resposta */
   public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "namePart") String namePart) {
	   /* Faz a consulta de usuários com nomes contendo uma parte em comum. */
	   List<Usuario> listaUsuarios = usuarioRepository.buscarPorNome(namePart.trim().toUpperCase());
	   return new ResponseEntity<List<Usuario>>(listaUsuarios, HttpStatus.OK);
   }
}
