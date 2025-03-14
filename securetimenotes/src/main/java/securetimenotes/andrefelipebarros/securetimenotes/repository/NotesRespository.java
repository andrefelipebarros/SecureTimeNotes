package securetimenotes.andrefelipebarros.securetimenotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import securetimenotes.andrefelipebarros.securetimenotes.model.note.Note;
import securetimenotes.andrefelipebarros.securetimenotes.model.user.User;

public interface NotesRespository extends JpaRepository<Note, Long>{
    List<Note> findByUser(User user);
}
