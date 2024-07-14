package br.com.teste.utils;

import br.com.teste.dto.UsuarioDto;
import br.com.teste.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioUtils {
    public UsuarioDto usuarioToDto(Usuario usuario) {
        return UsuarioDto.builder()
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .build();
    }

}
