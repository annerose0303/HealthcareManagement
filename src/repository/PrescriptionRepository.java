package repository;

import model.Prescription;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PrescriptionRepository implements Repository<Integer, Prescription> {

    private final Map<Integer, Prescription> store = new ConcurrentHashMap<>();

    @Override
    public List<Prescription> findAll() {
        List<Prescription> list = new ArrayList<>(store.values());
        // Stable ordering for GUI: newest first if date exists, otherwise by id
        list.sort(Comparator
                .comparing(Prescription::getDateIssued, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparingInt(Prescription::getPrescriptionId));
        return list;
    }

    @Override
    public Optional<Prescription> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(Prescription prescription) {
        store.put(prescription.getPrescriptionId(), prescription); // upsert
    }

    @Override
    public boolean deleteById(Integer id) {
        return store.remove(id) != null;
    }

    /** Useful for creating new Prescription records. */
    public int nextId() {
        return store.keySet().stream().mapToInt(Integer::intValue).max().orElse(0) + 1;
    }
}
