package securetimenotes.andrefelipebarros.securetimenotes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import securetimenotes.andrefelipebarros.securetimenotes.model.note.Note;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.User;
import securetimenotes.andrefelipebarros.securetimenotes.repository.NotesRespository;
import securetimenotes.andrefelipebarros.securetimenotes.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("user")
@Tag(name = "Notas do Usuário", description = "Endpoints para criar e visualizar notas do usuário autenticado.")
@SecurityRequirement(name = "bearerAuth")
public class NotesController {

    @Autowired
    NotesRespository noteRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Listar notas do usuário", 
                description = "Retorna todas as notas do usuário autenticado. É necessário fornecer um token de autenticação.")
    public List<Note> getNotes(@AuthenticationPrincipal User user) {
        return noteRepository.findByUser(user);
    }

    @PostMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Criar nova nota", 
                description = "Cria uma nova nota associada ao usuário autenticado. Requer autenticação.")
    public void postNotes(@AuthenticationPrincipal User user, @RequestBody Note note) {
        note.setUser(user);
        noteRepository.save(note);
    }
}
