package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ch.qos.logback.core.util.StringUtil;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor,
            EntitySQLMetaData<T> entitySQLMetaData,
            EntityClassMetaData<T> entityClassMetaData
    ) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                this::findByIdResultSetHandler
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                this::findAllResultSetHandler
        ).orElse(new ArrayList<>());
    }

    @Override
    public long insert(Connection connection, T object) {
        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                getFieldValuesForInsert(object)
        );
    }

    @Override
    public void update(Connection connection, T object) {
        dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getUpdateSql(),
                getFieldValuesForUpdate(object)
        );
    }

    private T findByIdResultSetHandler(ResultSet rs) {
        try {
            if (rs.next()) {
                return createObjectFromResultSet(rs);
            }
            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private ArrayList<T> findAllResultSetHandler(ResultSet rs) {
        ArrayList<T> resultList = new ArrayList<>();
        try {
            while (rs.next()) {
                T object = createObjectFromResultSet(rs);
                resultList.add(object);
            }
            return resultList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private T createObjectFromResultSet(ResultSet rs) throws
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchFieldException,
            SQLException
    {
        T object = entityClassMetaData.getConstructor().newInstance();
        for (Field field : entityClassMetaData.getAllFields()) {
            String fieldName = field.getName();
            Field objectField = object.getClass().getDeclaredField(fieldName);
            if (!objectField.canAccess(object)) {
                objectField.setAccessible(true);
            }
            objectField.set(object, rs.getObject(fieldName));
        }
        return object;
    }

    private List<Object> getFieldValues(T object, List<Field> fields) {
        List<Object> values = new ArrayList<>();
        for (Field field : fields) {
            try {
                Object fieldValue = object.getClass().getMethod(getterName(field)).invoke(object);
                values.add(fieldValue);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return values;
    }

    private List<Object> getFieldValuesForUpdate(T object) {
        List<Field> valuesForUpdate = new ArrayList<>(entityClassMetaData.getFieldsWithoutId());
        valuesForUpdate.add(entityClassMetaData.getIdField());

        return getFieldValues(object, valuesForUpdate);
    }

    private List<Object> getFieldValuesForInsert(T object) {
        return getFieldValues(object, entityClassMetaData.getFieldsWithoutId());
    }

    private String getterName(Field field) {
        return String.format("get%s", StringUtil.capitalizeFirstLetter(field.getName()));
    }
}
