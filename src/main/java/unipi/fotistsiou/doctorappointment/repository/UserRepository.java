package unipi.fotistsiou.doctorappointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import unipi.fotistsiou.doctorappointment.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET " +
            "u.firstName = :firstName, " +
            "u.lastName = :lastName, " +
            "u.email = :email, " +
            "u.telephone = :telephone, " +
            "u.address = :address, " +
            "u.specialization = :specialization " +
            "WHERE u.id = :id")
    void updateUser(
            @Param("id") Long id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("telephone") String telephone,
            @Param("address") String address,
            @Param("specialization") String specialization
    );
}