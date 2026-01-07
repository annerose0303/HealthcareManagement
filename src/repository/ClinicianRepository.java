package repository;

import model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClinicianRepository implements Repository<Integer, User> {
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparingInt(User::getUserId));
        return list;
    }


    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void save(User entity) {
        store.put(entity.getUserId(), entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        return store.remove(id) != null;
    }
}
