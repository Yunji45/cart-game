package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.ManageProduct.Product;
import java.sql.ResultSet;
import application.Cart;
import application.History;
import java.sql.Timestamp;
import java.sql.Statement;




public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:8889/JavaPro";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "updatedata";

    private Connection connection;

    public DatabaseManager() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC driver class not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertUser(String username, String email, String password, String gender, String nationality, int age, String role, String number) throws SQLException {
        try {
            // Nonaktifkan otomatis commit
            connection.setAutoCommit(false);

            String query = "INSERT INTO users (username, email, password, gender, nationality, age, role, number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, gender);
                statement.setString(5, nationality);
                statement.setInt(6, age);
                statement.setString(7, role);
                statement.setString(8, number);

                statement.executeUpdate();
            }

            // Commit secara manual
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaksi jika terjadi kesalahan
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            throw e; // Lempar kembali SQLException setelah rollback
        } finally {
            try {
                // Set otomatis commit kembali ke true
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
    }

    public User getUserFromDatabase(String email, String password) {
        String query = "SELECT id, username, email, password, number FROM users WHERE email = ? AND password = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    // Retrieve email, password, and number from the result set
                    String userEmail = resultSet.getString("email");
                    String userPassword = resultSet.getString("password");
                    String userNumber = resultSet.getString("number");

                    return new User(id, username, userEmail, userPassword, userNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public void insertUser(String email, String password, String gender, String nationality, int age, String role) throws SQLException {
//        try {
//            // Nonaktifkan otomatis commit
//            connection.setAutoCommit(false);
//
//            String query = "INSERT INTO users (email, password, gender, nationality, age, role) VALUES (?, ?, ?, ?, ?, ?)";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, email);
//                statement.setString(2, password);
//                statement.setString(3, gender);
//                statement.setString(4, nationality);
//                statement.setInt(5, age);
//                statement.setString(6, role);
//
//                statement.executeUpdate();
//            }
//
//            // Commit secara manual
//            connection.commit();
//        } catch (SQLException e) {
//            // Rollback transaksi jika terjadi kesalahan
//            try {
//                connection.rollback();
//            } catch (SQLException rollbackException) {
//                rollbackException.printStackTrace();
//            }
//
//            throw e; // Lempar kembali SQLException setelah rollback
//        } finally {
//            try {
//                // Set otomatis commit kembali ke true
//                connection.setAutoCommit(true);
//            } catch (SQLException autoCommitException) {
//                autoCommitException.printStackTrace();
//            }
//        }
//    }
//    
//    public User getUserFromDatabase(String email, String password) {
//        String query = "SELECT id, username, email, password FROM users WHERE email = ? AND password = ?";
//        
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, email);
//            statement.setString(2, password);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    int id = resultSet.getInt("id");
//                    String username = resultSet.getString("username");
//                    // Retrieve email and password from the result set
//                    String userEmail = resultSet.getString("email");
//                    String userPassword = resultSet.getString("password");
//
//                    return new User(id, username, userEmail, userPassword);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    
    // Metode untuk menambah produk ke database
    public void addProduct(String name, String brand, double price, int stock) {
        String query = "INSERT INTO product (name, brand, price, stock) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, brand);
            statement.setDouble(3, price);
            statement.setInt(4, stock);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String brand = resultSet.getString("brand");
                int price = resultSet.getInt("price");
                int stock = resultSet.getInt("stock");

                productList.add(new Product(name, brand, price, stock));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    // Metode untuk mengupdate stok produk di database
    public void updateStock(String name, int quantity) {
        String query = "UPDATE product SET stock = ? WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantity);
            statement.setString(2, name);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menghapus produk dari database
    public void deleteProduct(String name) {
        String query = "DELETE FROM product WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addToCart(int userId, int productId, int quantity) {
        // Implement logic to add the selected product to the cart with the specified quantity
        // Update the SQL query to insert data into the 'cart' table
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);
            
            preparedStatement.executeUpdate();
            System.out.println("Added to Cart: User ID " + userId + ", Product ID " + productId + ", Quantity " + quantity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Product> getCartItems(int userId) {
        List<Product> cartItems = new ArrayList<>();
        String query = "SELECT p.name, p.brand, p.price, p.stock, c.quantity " +
                       "FROM product p JOIN cart c ON p.id = c.product_id WHERE c.user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String brand = resultSet.getString("brand");
                    int price = resultSet.getInt("price");
                    int stock = resultSet.getInt("stock");
                    int quantity = resultSet.getInt("quantity");

                    Product product = new Product(name, brand, price, stock);
                    product.setQuantity(quantity);  // Menambahkan kuantitas ke objek Product
                    cartItems.add(product);
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately (log or throw a custom exception)
            System.err.println("Error fetching cart items: " + e.getMessage());
            e.printStackTrace();
        }

        return cartItems;
    }
    
 // Di dalam kelas DatabaseManager atau kelas yang sejenis
    public void removeFromCart(int userId, int productId) {
        String query = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void addHistoryEntry(History history) {
        // Kolom 'transaction_id' diabaikan karena akan diisi otomatis oleh database (Auto Increment)
        String query = "INSERT INTO history (user_id, product_id, quantity, total_price, transaction_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, history.getUserId());
            statement.setInt(2, history.getProductId());
            statement.setInt(3, history.getQuantity());
            statement.setInt(4, history.getTotalPrice());

            // Mengonversi String ke Timestamp jika diperlukan
            String dateString = history.getTransactionDate();
            Timestamp timestamp = Timestamp.valueOf(dateString);

            statement.setTimestamp(5, timestamp);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating history entry failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    history.setTransactionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating history entry failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
//    public void addHistoryEntry(History history) {
//        // Kolom 'transaction_id' diabaikan karena akan diisi otomatis oleh database (Auto Increment)
//        String query = "INSERT INTO history (user_id, product_id, quantity, total_price, transaction_date) VALUES (?, ?, ?, ?, ?)";
//
//        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            statement.setInt(1, history.getUserId());
//            statement.setInt(2, history.getProductId());
//            statement.setInt(3, history.getQuantity());
//            statement.setInt(4, history.getTotalPrice());
//            statement.setTimestamp(5, history.getTransactionDate());
//
//            int affectedRows = statement.executeUpdate();
//
//            if (affectedRows == 0) {
//                throw new SQLException("Creating history entry failed, no rows affected.");
//            }
//
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    history.setTransactionId(generatedKeys.getInt(1));
//                } else {
//                    throw new SQLException("Creating history entry failed, no ID obtained.");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    
    public void clearCart(int userId) {
        String query = "DELETE FROM cart WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<History> getHistoryEntries(int userId) {
        List<History> historyEntries = new ArrayList<>();
        String query = "SELECT * FROM history WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int transactionId = resultSet.getInt("transaction_id");
                    int productId = resultSet.getInt("product_id");
                    int quantity = resultSet.getInt("quantity");
                    int totalPrice = resultSet.getInt("total_price");

                    // Handle the timestamp column type appropriately
                    Timestamp transactionDate = null;
                    try {
                        transactionDate = resultSet.getTimestamp("transaction_date");
                    } catch (SQLException timestampException) {
                        // Handle the exception or log a message
                        timestampException.printStackTrace();
                    }

                    // Create a History object and add it to the list
                    History history = new History(userId, productId, quantity, totalPrice, transactionDate);
                    history.setTransactionId(transactionId); // This can be omitted if transactionId is automatically filled by the database
                    historyEntries.add(history);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyEntries;
    }
    
    public List<History> getAllHistoryEntries() {
        List<History> historyEntries = new ArrayList<>();
        String query = "SELECT * FROM history";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int transactionId = resultSet.getInt("transaction_id");
                int userId = resultSet.getInt("user_id");
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");
                int totalPrice = resultSet.getInt("total_price");
                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");

                History history = new History(userId, productId, quantity, totalPrice, transactionDate);
                history.setTransactionId(transactionId);
                historyEntries.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyEntries;
    }





}