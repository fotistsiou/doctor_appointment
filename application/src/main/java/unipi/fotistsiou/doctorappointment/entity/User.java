package unipi.fotistsiou.doctorappointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Το email δεν μπορεί να είναι κενό.")
    @Column(name="email", nullable=false, unique=true)
    private String email;

    @NotEmpty(message = "Ο κωδικός πρόσβασης δεν μπορεί να είναι κενός.")
    @Column(name="password", nullable=false)
    private String password;

    @NotEmpty(message = "Το όνομα δεν μπορεί να είναι κενό.")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "Το επώνυμο δεν μπορεί να είναι κενό.")
    @Column(name="last_name", nullable=false)
    private String lastName;

    @NotEmpty(message = "Το τηλέφωνο δεν μπορεί να είναι άδειο.")
    @Column(name="telephone", nullable=false)
    private String telephone;

    @Column(name="address")
    private String address;

    @Column(name="specialization")
    private String specialization;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();
}