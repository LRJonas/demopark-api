package com.jonas.demoparkapi.service;

import com.jonas.demoparkapi.entity.Usuario;
import com.jonas.demoparkapi.exception.EntityNotFoundException;
import com.jonas.demoparkapi.exception.UsernameUniqueViolationException;
import com.jonas.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; //injeção de dependência do repositório, precisa ser final para ser injetado
    private final PasswordEncoder passwordEncoder; //injeção de dependência do passwordEncoder, precisa ser final para ser injetado

    @Transactional //anotação que indica que o spring gerenciará a transação, abriando e fechando a conexão com o banco
    public Usuario salvar(Usuario usuario){
        try{
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); //criptografa a senha do usuário
            return usuarioRepository.save(usuario); //método para salvar um usuário
        }catch (DataIntegrityViolationException e){
            throw new UsernameUniqueViolationException(String.format("Username %s já Existente", usuario.getUsername())); //lança exceção caso o nome de usuário já exista
        }

    }

    @Transactional(readOnly = true) //readOnly true indica que a transação é apenas de leitura
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow( //método para buscar um usuário por id, orElseThrow lança uma exceção caso não encontre
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id)) //mensagem de exceção caso não encontre
        );
    }

    @Transactional //anotação que indica que o spring gerenciará a transação, abriando e fechando a conexão com o banco
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if(!novaSenha.equals(confirmaSenha)){ //verifica se a nova senha é igual a confirmação
            throw new RuntimeException("As senhas não conferem"); //lança exceção caso as senhas não conferem
        }
        Usuario user = buscarPorId(id); //busca o usuário por id

        if(!passwordEncoder.matches(senhaAtual, user.getPassword())){ //verifica se a senha atual é igual a senha do usuário
            throw new RuntimeException("Senha atual incorreta"); //lança exceção caso a senha atual seja incorreta
        }
        user.setPassword(passwordEncoder.encode(novaSenha)); //altera a senha
        return user; //salva o usuário apenas retornando o user pois o set password já altera o objeto
    }

    @Transactional(readOnly = true) //readOnly true indica que a transação é apenas de leitura
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll(); //método para buscar todos os usuários
    }

    @Transactional(readOnly = true) //readOnly true indica que a transação é apenas de leitura
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário username= %s não encontrado", username)) //mensagem de exceção caso não encontre
        );

    }
    @Transactional(readOnly = true) //readOnly true indica que a transação é apenas de leitura
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username); //método para buscar o role do usuário
    }
}
