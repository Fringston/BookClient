package fredrikkodar.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {

    private String token;

}
