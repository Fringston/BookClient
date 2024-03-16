package fredrikkodar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "Author ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "-------------------------";
    }

}
