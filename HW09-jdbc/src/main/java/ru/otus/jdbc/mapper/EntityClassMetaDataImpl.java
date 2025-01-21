package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (Exception ex) {
            throw new RuntimeException();
        }

    }

    @Override
    public Field getIdField() {
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return List.of();
    }
}
