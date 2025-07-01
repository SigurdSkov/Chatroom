package entities;

import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
public class UserEntity {
    //Genovervej om der skal være et ID således. Username + password burde vel være nok?
    //Also: UUID som Id er virkeligt usikkert, lmao
    @Id
    UUID userId;

    String username;
    String password;
}
