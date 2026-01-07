package repository;

import model.Patient;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PatientRepository implements Repository<Integer, Patient> {


    private final Map<Integer, Patient> store = new ConcurrentHashMap<>();

    @Override
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparingInt(Patient::getUserId));
        return list;
    }

    @Override
    public Optional<Patient> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Patient> findByNhsNumber(String nhsNumber) {
        if (nhsNumber == null) return Optional.empty();
        String target = nhsNumber.trim();
        return store.values().stream()
                .filter(p -> p.getNhsNumber() != null && p.getNhsNumber().trim().equalsIgnoreCase(target))
                .findFirst();
    }

    @Override
    public void save(Patient patient) {
        store.put(patient.getUserId(), patient); // upsert
    }

    @Override
    public boolean deleteById(Integer id) {
        return store.remove(id) != null;
    }

    /** Useful for creating new Patient records. */
    public int nextId() {
        return store.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }
}
