package lk.ijse.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String password;
    private String role;
    private String username;

    public UserDTO(String u_id, String u_name, String role) {
        this.userId = u_id;
        this.username = u_name;
        this.role = role;
    }
}
