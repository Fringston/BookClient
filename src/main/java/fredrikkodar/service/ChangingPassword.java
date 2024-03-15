package fredrikkodar.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangingPassword {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}