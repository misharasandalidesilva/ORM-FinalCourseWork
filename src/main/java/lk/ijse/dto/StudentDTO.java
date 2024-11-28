package lk.ijse.dto;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class StudentDTO {

    private String id;

    private String name;

    private String address;

    private String tel;

    private String regDate;

//    public StudentDTO(String id, String name, String address, String tel) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.tel = tel;
//    }
}
