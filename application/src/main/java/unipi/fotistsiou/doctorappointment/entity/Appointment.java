package unipi.fotistsiou.doctorappointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "H ημερομηνία δεν μπορεί να είναι κενή.")
    @Column(name="appointment_date", nullable=false)
    private String appointmentDate;

    @NotEmpty(message = "H ώρα δεν μπορεί να είναι κενή.")
    @Column(name="appointment_time", nullable=false)
    private String appointmentTime;

    @Column(name="booked", nullable=false)
    private int booked;

    @Column(name="reason")
    private String reason;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
    private User doctor;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;
}