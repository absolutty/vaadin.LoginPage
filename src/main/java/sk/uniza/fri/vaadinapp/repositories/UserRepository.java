package sk.uniza.fri.vaadinapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sk.uniza.fri.vaadinapp.models.Gender;
import sk.uniza.fri.vaadinapp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query( "UPDATE User u " +
            "SET u.email = :newEmail, " +
            "u.firstName = :newFirstName, " +
            "u.lastName = :newLastName, " +
            "u.password = :newPassword, " +
            "u.gender = :newGender, " +
            "u.role = :newRole " +
            "WHERE u.id = :userId")
    void updateUserWithId(
            @Param("userId") Long userId,
            @Param("newEmail") String newEmail,
            @Param("newFirstName") String newFirstName,
            @Param("newLastName") String newLastName,
            @Param("newPassword") String newPassword,
            @Param("newGender") Gender newGender,
            @Param("newRole") String newRole
    );

}
