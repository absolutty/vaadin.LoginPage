package sk.uniza.fri.vaadinapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY) private Long id;
    @Column(name = "EMAIL") private String email;
    @Column(name = "PASSWORD") private String password;
    @Column(name = "FIRST_NAME") private String firstName;
    @Column(name = "LAST_NAME") private String lastName;
    @Column(name = "GENDER") private String gender;
    @Column(name = "ROLE") private String role;

}
