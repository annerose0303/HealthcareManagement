package repository;

import model.Appointment;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AppointmentRepository implements Repository<Integer, Appointment> {

    private final Map<Integer, Appointment> store = new ConcurrentHashMap<>();

    @Override
    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>(store.values());
        // Stable ordering for GUI: by date then id (handles null dates safely)
        list.sort(Comparator
                .comparing(Appointment::getDate, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparingInt(Appointment::getAppointmentId));
        return list;
    }

    @Override
    public Optional<Appointment> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(Appointment appointment) {
        store.put(appointment.getAppointmentId(), appointment); // upsert
    }

    @Override
    public boolean deleteById(Integer id) {
        return store.remove(id) != null;
    }

    /** Useful for creating new Appointment records. */
    public int nextId() {
        return store.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }
}
