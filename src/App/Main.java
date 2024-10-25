package App;

import Model.Category;
import Model.Product;
import Service.CategoryDaoImp;
import Service.ProductDaoImpl;

import java.util.List;

public class Main {
  public static void main(String[] args) {

    CategoryDaoImp categoryDao = new CategoryDaoImp();
    ProductDaoImpl productDao = new ProductDaoImpl();

//    Category electronics = new Category("All electronic items", "Electronics");
//    Category groceries = new Category("All groceries", "Groceries");
//    categoryDao.createe(electronics);
//    categoryDao.createe(groceries);

    // Fetch all categories
    List<Category> allCategories = categoryDao.getAll();

    // Print all categories using Streams
    System.out.println("All Categories:");
    allCategories.forEach(category -> System.out.println(category.getName() + ": " + category.getDescription()));

    // Create and save Products
//    Product phone = new Product("Mouse", 799.99, 100L, electronics,1L);
//    Product tv = new Product("Mac", 1499.99, 50L, electronics,2L);
//    Product apple = new Product( "Iphone", 0.5, 1000L,electronics, 3L);
//    productDao.createe(phone);
//    productDao.createe(tv);
//    productDao.createe(apple);


    // Fetch all products
    List<Product> allProducts = productDao.getAll();

    // Filter products by category using streams
    // Fetch a single product by ID
    // Update a product
    // Delete a product (assuming product ID 2 exists)
    // Example using map to transform product names

  }
}
