package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
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
                rs -> {
                    try {
                        if (rs.next()) {
                            return entityClassMetaData.getConstructor().newInstance(getFieldValues(rs));
                        }
                        return null;
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectAllSql(),
                Collections.emptyList(),
                rs -> {
                    ArrayList<T> resultList = new ArrayList<>();
                    try {
                        while (rs.next()) {
                            resultList.add(entityClassMetaData.getConstructor().newInstance(getFieldValues(rs)));
                        }
                        return resultList;
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
        ).orElseThrow(() -> new RuntimeException());
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

    private Object[] getFieldValues(ResultSet rs) {
        try {
            List<Field> fieldList = entityClassMetaData.getAllFields();
            Object[] objects = new Object[fieldList.size()];
            objects[0] = rs.getLong(entityClassMetaData.getIdField().getName());
            for (int i = 1; i < fieldList.size(); i++) {
                objects[i] = rs.getObject(fieldList.get(i).getName());
            }
            return objects;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
