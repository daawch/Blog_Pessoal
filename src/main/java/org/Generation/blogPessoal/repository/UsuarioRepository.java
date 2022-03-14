package org.Generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.Generation.blogPessoal.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsuario(String usuario);

    public List <User> findAllByNomeContainingIgnoreCase(String nome);
}