package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntityClassMetaDataClientTest {

    private final EntityClassMetaData<Client> entityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);

    @Test
    public void shouldReturnName() {
        String name = entityClassMetaData.getName();

        assertEquals("Client", name);
    }

    @Test
    public void shouldReturnFieldsWithoutId() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();

        assertEquals(1, fieldsWithoutId.size());
    }

    @Test
    public void shouldReturnAllFields() {
        List<Field> fieldsWithoutId = entityClassMetaData.getAllFields();

        assertEquals(2, fieldsWithoutId.size());
    }

    @Test
    public void shouldReturnIdField() {
        Field idField = entityClassMetaData.getIdField();

        assertNotNull(idField);
        assertEquals("id", idField.getName());
    }
}