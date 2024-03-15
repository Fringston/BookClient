package fredrikkodar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private Role role;

    public User(Long l, String username, String password) {
    }

    public User(String username, String password) {
    }
}
