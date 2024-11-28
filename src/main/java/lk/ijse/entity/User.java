package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String u_id;
    private String password;
    private String role;
    private String u_name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> student;

    public User(String userId, String password, String role, String username) {
        this.u_id = userId;
        this.u_name = username;
        this.password = password;
        this.role = role;
    }
}
