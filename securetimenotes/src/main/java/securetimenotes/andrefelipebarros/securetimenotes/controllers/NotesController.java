package securetimenotes.andrefelipebarros.securetimenotes.controllers;

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
public class NotesController {
    @Autowired
    NotesRespository noteRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    public List<Note> getNotes(@AuthenticationPrincipal User user) {
        return noteRepository.findByUser(user);
    }

    @PostMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    public void postNotes(@AuthenticationPrincipal User user, @RequestBody Note note) {
        note.setUser(user);
        noteRepository.save(note);
    }
}
