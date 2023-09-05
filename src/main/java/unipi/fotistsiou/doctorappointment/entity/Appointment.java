package unipi.fotistsiou.doctorappointment.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="address")
    private String reason;

    @Column(name="appointment_date")
    private String appointmentDate;

    @Column(name="appointment_time")
    private String appointmentTime;

    @Column(name="booked", nullable=false)
    private int booked;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private User doctor;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;
}