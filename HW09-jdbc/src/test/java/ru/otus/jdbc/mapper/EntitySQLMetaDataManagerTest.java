package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitySQLMetaDataManagerTest {

    private final EntityClassMetaDataImpl<Manager> entityClassMetaData = new EntityClassMetaDataImpl<>(Manager.class);

    private final EntitySQLMetaDataImpl<Manager> entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);

    @Test
    void getSelectAllSql() {
        String selectAllSql = entitySQLMetaData.getSelectAllSql();

        assertEquals("select * from Manager", selectAllSql);
    }

    @Test
    void getSelectByIdSql() {
        String selectByIdSql = entitySQLMetaData.getSelectByIdSql();

        assertEquals("select * from Manager where no = ?", selectByIdSql);
    }

    @Test
    void shouldReturnInsertSqlForManager() {
        String insertSql = entitySQLMetaData.getInsertSql();

        assertEquals("insert into Manager(label,param1) values (?,?)", insertSql);
    }


    @Test
    void shouldReturnUpdateSqlForManager() {
        String updateSql = entitySQLMetaData.getUpdateSql();

        assertEquals("update Manager set label = ?, param1 = ? where no = ?", updateSql);
    }
}