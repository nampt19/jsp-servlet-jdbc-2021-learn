package dao;

import mapper.RowMapper;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);

    Long insert(String sql, Object... parameters);

    void update(String sql, Object... parameters);

}
