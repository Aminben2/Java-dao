package Service;

import Dao.Dao;
import Model.Category;
import Model.Product;
import Util.DatabaseConnection;
import Util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements Dao<Product> {
  Connection connection;
  PreparedStatement statement;
  ResultSet data;
  int res;

  public ProductDaoImpl() {
//    connection = DatabaseConnection.connection;
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
      statement =
          connection.prepareStatement(
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
      statement =
          connection.prepareStatement(
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

  @Override
  public Optional<Product> getById(Long id) {
    if (id == null) {
      return Optional.empty();
    }

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpaDemo");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    Product product = null;
    try {
      entityManager.getTransaction().begin();
      product = entityManager.find(Product.class, id);
      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      throw new RuntimeException("Failed to fetch product by id: " + e.getMessage(), e);
    } finally {
      entityManager.close();
      entityManagerFactory.close();
    }

    return Optional.ofNullable(product);
  }

  @Override
  public List<Product> getAll() {
    EntityManager entityManager = EntityManagerProvider.getEntityManager();

    List<Product> products = null;
    try {
      entityManager.getTransaction().begin();
      products =
          entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
    } finally {
      entityManager.close();
    }

    return products;
  }

  @Override
  public Optional<Product> update(Product obj) {
    if (obj == null || obj.getId() == null) {
      return Optional.empty();
    }
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();

      Optional<Product> productOptional = getById(obj.getId());
      if (productOptional.isPresent()) {
        Product existingProduct = productOptional.get();
        existingProduct.setCategory(obj.getCategory());
        existingProduct.setDescription(obj.getDescription());
        existingProduct.setSdr(obj.getSdr());
        existingProduct.setPrice(obj.getPrice());
        existingProduct.setQuantity(obj.getQuantity());

        entityManager.getTransaction().commit();
        return Optional.of(existingProduct);
      } else {
        return Optional.empty();
      }
    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Product createe(Product obj) {
    if (obj == null) {
      System.out.println("Product cannot be null.");
      return null;
    }

    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(obj);
      entityManager.getTransaction().commit();
      return obj;
    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      System.out.println("Failed to create product: " + e.getMessage());
      return null;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public int deleteByid(Long id) {
    if (id == null) {
      return 0;
    }

    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();
      Product product = entityManager.find(Product.class, id);
      if (product != null) {
        entityManager.remove(product);
        entityManager.getTransaction().commit();
        return 1;
      } else {
        return 0;
      }
    } catch (PersistenceException e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      System.out.println("Failed to delete product: " + e.getMessage());
      return 0;
    } finally {
      entityManager.close();
    }
  }
}
