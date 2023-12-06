package unipi.fotistsiou.doctorappointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unipi.fotistsiou.doctorappointment.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {}