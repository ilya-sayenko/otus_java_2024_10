package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "select * from %s where %s = ?",
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName()
        );
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String fieldNames = fieldsWithoutId
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
        String values = "?,".repeat(fieldsWithoutId.size());
        values = values.substring(0, values.length() - 1);

        return String.format(
                "insert into %s(%s) values (%s)",
                entityClassMetaData.getName(),
                fieldNames,
                values
        );
    }

    @Override
    public String getUpdateSql() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("update ");
        stringBuilder.append(entityClassMetaData.getName());
        stringBuilder.append(" set ");

        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            stringBuilder.append(field.getName());
            stringBuilder.append(" = ?, ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(" where ");
        stringBuilder.append(entityClassMetaData.getIdField().getName());
        stringBuilder.append(" = ?");

        return stringBuilder.toString();
    }
}
