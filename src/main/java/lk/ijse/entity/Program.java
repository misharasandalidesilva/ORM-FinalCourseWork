package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class Program {
    @Id
    private String p_id;
    private String p_name;
    private String duration;
    private double fee;

    public Program(String programId) {
        this.p_id = programId;
    }
}
