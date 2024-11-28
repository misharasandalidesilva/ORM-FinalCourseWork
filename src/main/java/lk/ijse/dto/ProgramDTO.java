package lk.ijse.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProgramDTO {

    private String p_id;
    private String p_name;
    private String duration;
    private double fee;

    public ProgramDTO(String p_id, String duration, double fee, String p_name) {
        this.p_id = p_id;
        this.duration = duration;
        this.fee = fee;
        this.p_name = p_name;
    }

}
