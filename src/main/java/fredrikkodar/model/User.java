package fredrikkodar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private Role role;

    //Clara
    //behöver till Admin-kontroll
    private Set<Role> authorities;

    public User(Long l, String username, String password) {
    }
}
