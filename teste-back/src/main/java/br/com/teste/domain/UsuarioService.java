package br.com.teste.domain;

import br.com.teste.dto.UsuarioDto;
import br.com.teste.entity.Usuario;
import br.com.teste.exceptions.UsuarioGenericException;
import br.com.teste.utils.UsuarioUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    private List<Usuario> usuariosList = new ArrayList<>(List.of(new Usuario("prova@teste.ai", "159358")));

    public UsuarioDto verificaExistencia(String email, String senha) throws UsuarioGenericException {
        if(!Objects.equals(email, usuariosList.stream().findFirst().get().getEmail()) ||
                !Objects.equals(senha, usuariosList.stream().findFirst().get().getSenha())){
            //erro frontend
            throw new UsuarioGenericException("Senha inválida");
        }


        return UsuarioDto.builder()
                .email(email)
                .senha(senha)
                .build();
    }

    public UsuarioDto resetSenha(String email, String senha) throws UsuarioGenericException {
        if(!Objects.equals(email, "prova@teste.ai")){
            throw new UsuarioGenericException("Email inválido");
        }
        usuariosList.stream().iterator().next().setSenha(senha);

        return UsuarioUtils.usuarioToDto(usuariosList.stream().findFirst().get());
    }
}
