package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    // The connections list
    private List<Connection> connections;
    // ConnectionPool instance for singleton implementation
    private static ConnectionPool instance;
    private static final int MAX_CONNECTIONS = 5;
    private final String connectionURL = "jdbc:mysql://localhost:3306/northwind?useUnicode=true&serverTimezone=UTC";

    /**
     * Constructs a new connection pool with 5(MAX_CONNECTIONS) connections available.
     */
    private ConnectionPool(){
        connections = new ArrayList<>();
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            Connection connection = createConnection();
            if(connection != null)
                connections.add(connection);
            else System.out.println("Error adding null connection to the pool.");
        }
    }

    /**
     * Singleton implementation
     * Checks whether a connection pool already exists if so returns the old instance else creates a new one.
     * @return a connection pool.
     */

    public static ConnectionPool getInstance(){
        return (instance == null) ? instance = new ConnectionPool(): instance;
    }

    /**
     * Provides one available connection from the pool, if no connection is available then wait.
     * @return An available connection.
     */
    public synchronized Connection getConnection(){
        while (connections.size() == 0){
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        Connection connection = connections.get(0);
        connections.remove(0);
        return connection;
    }

    /**
     * Creates a new connection to DB.
     * @return a connection.
     */
    private Connection createConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionURL,"root","Shimmy$$Boy");
        } catch (SQLException ignored) {
        }
        return connection;
    }

    /**
     * Restores an unused connection back to the pool.
     * @param connection an unused connection
     */
    public synchronized void restoreConnection(Connection connection){
        connections.add(connection);
        notify();
    }

    /**
     * Closes all connections to DB, if connection is busy then wait.
     */
    public synchronized void closeAllConnections(){
        while (connections.size() < MAX_CONNECTIONS){
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        for(Connection connection : connections){
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
