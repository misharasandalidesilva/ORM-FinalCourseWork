package lk.ijse.tdm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RegPaymentTM {
    private int regId;
    private String name;
    private String p_name;
    private double fee;
    private double paidAmount;
}
