package securetimenotes.andrefelipebarros.securetimenotes.model.note;

import securetimenotes.andrefelipebarros.securetimenotes.model.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(unique = true, length = 9)
    private String titule;

    private String content;

    // Relacionamento ManyToOne com User
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

}
