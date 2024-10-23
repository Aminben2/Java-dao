package Service;

import Dao.Dao;
import Model.Category;
import Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImp implements Dao<Category> {

  Connection connection;
  PreparedStatement statement;
  ResultSet data;
  int res;

  public CategoryDaoImp() {
    connection = DatabaseConnection.connection;
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
}
