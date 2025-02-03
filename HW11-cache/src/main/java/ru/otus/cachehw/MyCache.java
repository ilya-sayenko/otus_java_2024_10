package ru.otus.cachehw;

import ru.otus.cachehw.exception.ListenerException;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import static ru.otus.cachehw.Action.GET;
import static ru.otus.cachehw.Action.PUT;
import static ru.otus.cachehw.Action.REMOVE;

public class MyCache<K, V> implements HwCache<K, V> {

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();

    private final List<HwListener<K,V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, PUT);
    }

    @Override
    public void remove(K key) {
        V value = cache.get(key);
        cache.remove(key);
        notifyListeners(key, value, REMOVE);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notifyListeners(key, value, GET);
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, Action action) {
        try {
            listeners.forEach(listener -> listener.notify(key, value, action.name()));
        } catch (Exception ex) {
            throw new ListenerException(String.format("Listener exception: %s", ex.getMessage()));
        }

    }
}
