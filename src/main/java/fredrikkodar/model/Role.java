package fredrikkodar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Long roleId;
    private String authority;


    // Clara
    // Testar denna metod till AdminMenu.java
    public static Role valueOf(String authority) {
        Role r = new Role();
        r.setAuthority(authority);
        // Set roleId to a default value, or fetch it from the database
        r.setRoleId(0L);
        return r;
    }
}
