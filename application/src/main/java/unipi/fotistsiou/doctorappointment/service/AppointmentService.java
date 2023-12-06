package unipi.fotistsiou.doctorappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unipi.fotistsiou.doctorappointment.entity.Appointment;
import unipi.fotistsiou.doctorappointment.repository.AppointmentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(
        AppointmentRepository appointmentRepository
    ){
        this.appointmentRepository = appointmentRepository;
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAvailableAppointments() {
        List<Appointment> availableAppointments = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment appointment:appointments) {
            if (appointment.getBooked() == 0) {
                availableAppointments.add(appointment);
            }
        }
        return availableAppointments;
    }

    public List<Appointment> getAvailableUserAppointments(Long userId, String role) {
        List<Appointment> availableAppointments = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment appointment:appointments) {
            if (role.contains("ROLE_PATIENT")) {
                if (appointment.getPatient() != null && appointment.getPatient().getId().equals(userId)) {
                    availableAppointments.add(appointment);
                }
            } else if (role.contains("ROLE_DOCTOR")) {
                if (appointment.getDoctor().getId().equals(userId)) {
                    availableAppointments.add(appointment);
                }
            }
        }
        return availableAppointments;
    }

    public void saveAppointment(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setBooked(0);
        } else {
            appointment.setBooked(1);
        }
        appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }
}