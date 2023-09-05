package unipi.fotistsiou.doctorappointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unipi.fotistsiou.doctorappointment.entity.Appointment;
import unipi.fotistsiou.doctorappointment.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService (
        AppointmentRepository appointmentRepository
    ){
        this.appointmentRepository = appointmentRepository;
    }

    public Optional<Appointment> getById (Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public Appointment save (Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setBooked(0);
        } else {
            appointment.setBooked(1);
        }
        return appointmentRepository.save(appointment);
    }

    public void delete(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }
}