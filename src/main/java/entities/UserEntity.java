package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class UserEntity {
    @Id
    UUID userId;

    String username;
    String password;
}
