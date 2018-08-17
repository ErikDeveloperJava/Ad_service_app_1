package yourAd.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private int id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private UserRole role;
}

