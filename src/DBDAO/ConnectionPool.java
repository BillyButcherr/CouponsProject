package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private List<Connection> connections;
    private static ConnectionPool instance;
    private static final int MAX_CONNECTIONS = 5;
    private final String connectionURL = "jdbc:mysql://localhost:3306/northwind?useUnicode=true&serverTimezone=UTC";

    /**
     *
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
     *
     * @return
     */

    public static ConnectionPool getInstance(){
        return (instance == null) ? instance = new ConnectionPool(): instance;
    }

    /**
     *
     * @return
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
     *
     * @return
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
     *
     * @param connection
     */
    public synchronized void restoreConnection(Connection connection){
        connections.add(connection);
        notify();
    }

    /**
     *
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
