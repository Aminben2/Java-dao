package Service;

import Dao.Dao;
import Model.Category;
import Util.DatabaseConnection;
import Util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImp implements Dao<Category> {

  Connection connection;
  PreparedStatement statement;
  ResultSet data;
  int res;

  public CategoryDaoImp() {
//    connection = DatabaseConnection.connection;
  }

  @Override
  public Category findById(Long id) {
    try {
      statement = connection.prepareStatement("select * from category where id = ?");
      statement.setLong(1, id);
      data = statement.executeQuery();
      data.next();

      Category category = new Category();
      category.setId(data.getLong("id"));
      category.setName(data.getString("name"));
      category.setDescription(data.getString("description"));
      return category;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public List<Category> findAll() {
    try {
      statement = connection.prepareStatement("select * from category");
      data = statement.executeQuery();

      List<Category> categories = new ArrayList<>();
      while (data.next()){
        Category category = new Category();
        category.setId(data.getLong("id"));
        category.setName(data.getString("name"));
        category.setDescription(data.getString("description"));
        categories.add(category);
      }
      return categories;
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public void update(Long id, Category object) {
    try {
      statement = connection.prepareStatement("update category set name = ?, description = ? where id = ?");
      statement.setString(1, object.getName());
      statement.setString(2, object.getDescription());
      statement.setLong(3, object.getId());
      res = statement.executeUpdate();
      if (res == 1){
        System.out.println("Category updated");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void delete(Long id) {
    try {
      statement = connection.prepareStatement("delete from category where id = ?");
      statement.setLong(1, id);
      res = statement.executeUpdate();
      if (res == 1){
        System.out.println("Category deleted with id" + id);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void create(Category object) {
    try {
      statement = connection.prepareStatement("insert into category(name,description) values(?,?)");
      statement.setString(1, object.getName());
      statement.setString(2, object.getDescription());
      res = statement.executeUpdate();
      if (res == 1){
        System.out.println("Category created");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public Optional<Category> getById(Long id) {
    if (id == null) {
      return Optional.empty();
    }
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();
      Category category = entityManager.find(Category.class, id);
      entityManager.getTransaction().commit();
      return Optional.ofNullable(category);
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      return Optional.empty();
    } finally {
      entityManager.close();
    }
  }

  @Override
  public List<Category> getAll() {
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();
      List<Category> categories = entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
      entityManager.getTransaction().commit();
      return categories;
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      return List.of();
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Optional<Category> update(Category obj) {
    if (obj == null || obj.getId() == null) {
      return Optional.empty();
    }
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();
      Category category = entityManager.find(Category.class, obj.getId());
      if (category != null) {
        category.setName(obj.getName());
        category.setDescription(obj.getDescription());
        entityManager.merge(category);
        entityManager.getTransaction().commit();
        return Optional.of(category);
      }
      entityManager.getTransaction().commit();
      return Optional.empty();
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      return Optional.empty();
    } finally {
      entityManager.close();
    }
  }

  @Override
  public Category createe(Category obj) {
    if (obj == null) {
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
      Category category = entityManager.find(Category.class, id);
      if (category != null) {
        entityManager.remove(category);
        entityManager.getTransaction().commit();
        return 1;
      }
      entityManager.getTransaction().commit();
      return 0;
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      return 0;
    } finally {
      entityManager.close();
    }
  }
}
