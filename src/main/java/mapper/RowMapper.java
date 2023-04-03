package mapper;

import java.sql.ResultSet;

public interface RowMapper<T> {
    // convert rs to model
    T mapRow(ResultSet resultSet);
}
