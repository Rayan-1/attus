package br.com.teste.controller;

import br.com.teste.domain.UsuarioService;
import br.com.teste.dto.UsuarioDto;
import br.com.teste.exceptions.UsuarioGenericException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDto login(@RequestParam String email, @RequestParam String senha) throws UsuarioGenericException {
        log.info("chamou o login");
        return usuarioService.verificaExistencia(email, senha);
    }

    @PutMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDto resetSenha(@RequestParam String email,@RequestParam String novaSenha) throws UsuarioGenericException {
        log.info("chamou senha");
        return usuarioService.resetSenha(email, novaSenha);
    }
}
