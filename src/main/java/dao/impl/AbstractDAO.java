package dao.impl;

import dao.GenericDAO;
import mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractDAO<T> implements GenericDAO<T> {
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jspservletjdbc";
            String user = "root";
            String password = "1234";
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }

    @Override
    public List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        List<T> resultList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setParametersForStatement(statement, parameters);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                resultList.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                closeSQLConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resultList;
    }

    @Override
    public Long insert(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Long resultId = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParametersForStatement(statement, parameters);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                resultId = resultSet.getLong(1);
            }
            connection.commit();
            return resultId;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                closeSQLConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void update(String sql, Object... parameters) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            setParametersForStatement(statement, parameters);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                closeSQLConnection(connection, statement, resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setParametersForStatement(PreparedStatement statement, Object... parameters) {
        try {
            int index = 1;
            for (Object o : parameters) {
                if (o instanceof Integer)
                    statement.setInt(index, (Integer) o);
                else if (o instanceof Long)
                    statement.setLong(index, (Long) o);
                else if (o instanceof String)
                    statement.setString(index, (String) o);
                else if (o instanceof Timestamp)
                    statement.setTimestamp(index, (Timestamp) o);
                else if (o == null) {
                    statement.setObject(index, null);
                }
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeSQLConnection(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if (conn != null)
            conn.close();
        if (st != null)
            st.close();
        if (rs != null)
            rs.close();
    }

}
