package Service;

import Dao.Dao;
import Model.Category;
import Model.Product;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements Dao<Product> {
    Connection connection;
    PreparedStatement statement;
    ResultSet data;
    int res;

    public ProductDaoImpl() {
        connection = DatabaseConnection.connection;
    }

    @Override
    public Product findById(Long id) {
        try {
            statement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");
            statement.setLong(1, id);
            data = statement.executeQuery();
            data.next();

            Product product = new Product();
            product.setId(data.getLong("id"));
            product.setDescription(data.getString("description"));
            product.setPrice(data.getDouble("price"));
            product.setQuantity(data.getLong("quantity"));
            product.setSdr(data.getLong("sdr"));

            Category category = new Category();
            category.setId(data.getLong("category_id"));
            product.setCategory(category);

            return product;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            statement = connection.prepareStatement("SELECT * FROM product");
            data = statement.executeQuery();

            List<Product> products = new ArrayList<>();
            while (data.next()) {
                Product product = new Product();
                product.setId(data.getLong("id"));
                product.setDescription(data.getString("description"));
                product.setPrice(data.getDouble("price"));
                product.setQuantity(data.getLong("quantity"));
                product.setSdr(data.getLong("sdr"));

                Category category = new Category();
                category.setId(data.getLong("category_id"));
                product.setCategory(category);

                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void update(Long id, Product object) {
        try {
            statement = connection.prepareStatement(
                    "UPDATE product SET description = ?, price = ?, quantity = ?, sdr = ?, category_id = ? WHERE id = ?");
            statement.setString(1, object.getDescription());
            statement.setDouble(2, object.getPrice());
            statement.setLong(3, object.getQuantity());
            statement.setLong(4, object.getSdr());
            statement.setLong(5, object.getCategory().getId());
            statement.setLong(6, id);

            res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("Product updated");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            statement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
            statement.setLong(1, id);
            res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("Product deleted with id " + id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void create(Product object) {
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO product(description, price, quantity, sdr, category_id) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, object.getDescription());
            statement.setDouble(2, object.getPrice());
            statement.setLong(3, object.getQuantity());
            statement.setLong(4, object.getSdr());
            statement.setLong(5, object.getCategory().getId());

            res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("Product created");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
