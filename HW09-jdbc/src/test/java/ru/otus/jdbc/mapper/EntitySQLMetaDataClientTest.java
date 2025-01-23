package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitySQLMetaDataClientTest {

    private final EntityClassMetaDataImpl<Client> entityClassMetaData = new EntityClassMetaDataImpl<>(Client.class);

    private final EntitySQLMetaDataImpl<Client> entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);

    @Test
    void getSelectAllSql() {
        String selectAllSql = entitySQLMetaData.getSelectAllSql();

        assertEquals("select * from Client", selectAllSql);
    }

    @Test
    void getSelectByIdSql() {
        String selectByIdSql = entitySQLMetaData.getSelectByIdSql();

        assertEquals("select * from Client where id = ?", selectByIdSql);
    }

    @Test
    void shouldReturnInsertSqlForClient() {
        String insertSql = entitySQLMetaData.getInsertSql();

        assertEquals("insert into Client(name) values (?)", insertSql);
    }


    @Test
    void shouldReturnUpdateSqlForClient() {
        String updateSql = entitySQLMetaData.getUpdateSql();

        assertEquals("update Client set name = ? where id = ?", updateSql);
    }
}