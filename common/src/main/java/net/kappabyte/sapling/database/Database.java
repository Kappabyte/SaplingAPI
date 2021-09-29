/*
MIT License

Copyright (c) 2020 Jordyn Newnham

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package net.kappabyte.sapling.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public abstract class Database {
    // Connection to the database
    protected Connection connection;

    /**
     * Creates a new Database
     */
    protected Database() {
        this.connection = null;
    }

    /**
     * Checks if a connection is open with the database
     *
     * @return true if the connection is open
     * @throws SQLException if the connection cannot be checked
     */
    private boolean checkConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    /**
     * Gets the connection with the database
     *
     * @return Connection with the database, will initialise new connection if dead
     * @throws SQLException if cannot get a connection
     */
    public abstract Connection getConnection() throws SQLException;

    /**
     * Closes the connection with the database
     *
     * @throws SQLException if the connection cannot be closed
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Executes a SQL Query and returns a ResultSet
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return {@link ResultSet}
     * @throws SQLException If the query cannot be executed
     */
    public ResultSet query(String query) throws SQLException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(query);

        return statement.executeQuery();
    }

    /**
     * Executes a SQL Query
     * If the connection is closed, it will be opened
     *
     * @param query    Query to be run
     * @param consumer to pass {@link ResultSet} to
     * @throws SQLException If the query cannot be executed
     */
    public void query(String query, DatabaseAction<ResultSet> consumer) throws SQLException {
        ResultSet resultSet = query(query);

        consumer.accept(resultSet);

        resultSet.close();
        resultSet.getStatement().close();
    }

    /**
     * Executes a SQL Query and returns a {@link CompletableFuture} of a {@link ResultSet}
     * If the connection is closed, it will be opened
     *
     * @param query Query to be run
     * @return {@link CompletableFuture} containing {@link ResultSet} object
     * internally throws {@link SQLException} If the query cannot be executed
     */
    public CompletableFuture<ResultSet> queryAsync(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return query(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * Executes an Update SQL Update
     * See {@link java.sql.PreparedStatement#executeUpdate()}
     * If the connection is closed, it will be opened
     *
     * @param update Update to be run
     * @return result code, see {@link java.sql.PreparedStatement#executeUpdate()}
     * @throws SQLException If the query cannot be executed
     */
    public int update(String update) throws SQLException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        PreparedStatement statement = connection.prepareStatement(update);
        int result = statement.executeUpdate();

        statement.close();

        return result;
    }

    public int update(PreparedStatement statement) throws SQLException {
        if (!checkConnection()) {
            connection = getConnection();
        }

        int result = statement.executeUpdate();

        statement.close();

        return result;
    }

    /**
     * Executes an SQL update asynchronously
     *
     * @param update Update to be run
     * @return {@link CompletableFuture} containing result code, see {@link java.sql.PreparedStatement#executeUpdate()}
     * internally throws {@link SQLException}           If the query cannot be executed
     * internally throws {@link ClassNotFoundException} If the driver cannot be found; see {@link #getConnection()}
     */
    public CompletableFuture<Integer> updateAsync(String update) {
        return CompletableFuture.supplyAsync(() -> {
            int results = 0;
            try {
                results = update(update);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return results;
        });
    }

    public CompletableFuture<Integer> updateAsync(PreparedStatement statement) {
        return CompletableFuture.supplyAsync(() -> {
            int results = 0;
            try {
                results = update(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return results;
        });
    }
}
