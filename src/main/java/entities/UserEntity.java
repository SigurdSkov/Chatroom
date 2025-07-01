package entities;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class UserEntity {
    @Id
    UUID userId;

    String username;
    String password;
}
