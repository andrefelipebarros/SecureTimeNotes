package securetimenotes.andrefelipebarros.securetimenotes.model.note;

import securetimenotes.andrefelipebarros.securetimenotes.model.user.User;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Setter
@Getter
@Table(name = "Notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // Relacionamento ManyToOne com User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
