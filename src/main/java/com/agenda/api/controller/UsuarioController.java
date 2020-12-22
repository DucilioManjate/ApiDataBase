package com.agenda.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.api.model.Usuario;
import com.agenda.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired //injecao de dependecias conceito - SOLID eh um padrao de design de codigo
	private UsuarioRepository usuarioRepository;
	
	
	@GetMapping
	public ResponseEntity<List<Usuario>> buscarTodosUsuarios(){
		return ResponseEntity.ok(usuarioRepository.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id){
		Optional<Usuario> user = usuarioRepository.findById(id);
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();	
		}
		return ResponseEntity.ok(user.get());
		
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario user){
		
		Usuario saved = usuarioRepository.save(user);
		
		return new ResponseEntity<Usuario>(saved, HttpStatus.CREATED);
	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
		
		Optional<Usuario> user = usuarioRepository.findById(id);
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();			
		}
		Usuario saved = user.get();
		
		saved.setNome(usuario.getNome());
		saved.setEmail(usuario.getEmail());
		saved.setSenha(usuario.getSenha());
		saved = usuarioRepository.save(saved);
		return ResponseEntity.ok(saved);
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id){
		Optional<Usuario> user = usuarioRepository.findById(id);
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();			
		}
		usuarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}

}
