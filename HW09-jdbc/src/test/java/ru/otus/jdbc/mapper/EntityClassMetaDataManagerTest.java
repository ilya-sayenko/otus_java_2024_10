package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Manager;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntityClassMetaDataManagerTest {

    private final EntityClassMetaData<Manager> entityClassMetaData = new EntityClassMetaDataImpl<>(Manager.class);

    @Test
    public void shouldReturnName() {
        String name = entityClassMetaData.getName();

        assertEquals("Manager", name);
    }

    @Test
    public void shouldReturnFieldsWithoutId() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();

        assertEquals(2, fieldsWithoutId.size());
    }

    @Test
    public void shouldReturnAllFields() {
        List<Field> fieldsWithoutId = entityClassMetaData.getAllFields();

        assertEquals(3, fieldsWithoutId.size());
    }

    @Test
    public void shouldReturnIdField() {
        Field idField = entityClassMetaData.getIdField();

        assertNotNull(idField);
        assertEquals("no", idField.getName());
    }
}