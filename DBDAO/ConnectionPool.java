package DBDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {
    private Set<Connection> connections = new HashSet<>();
    private static final ConnectionPool instance = new ConnectionPool();
    private final String connectionURL = "jdbc:mysql://localhost:3306/northwind?useUnicode=true&serverTimezone=UTC";

    private ConnectionPool(){
        for (int i = 0; i < 5; i++) {
            Connection connection = createConnection();
            if(connection != null)
                connections.add(connection);
            else System.out.println("Error adding null connection to the pool.");
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection(){
        while (connections.isEmpty()){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Connection connection = connections.iterator().next();
        connections.remove(connection);
        return connection;
    }
    private Connection createConnection(){
        try{
            return DriverManager.getConnection(connectionURL,"root","Shimmy$$Boy");
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void restoreConnection(Connection connection){
        connections.add(connection);
    }
    public void closeAllConnections(){
        connections.stream().forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
