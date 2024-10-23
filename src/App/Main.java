package App;

import Service.CategoryDaoImp;
import Service.ProductDaoImpl;

public class Main {
  public static void main(String[] args) {

    // Initialize DAO implementations
    CategoryDaoImp categoryDao = new CategoryDaoImp();
    ProductDaoImpl productDao = new ProductDaoImpl();

    // Test CRUD operations for Category
    System.out.println("===== CATEGORY CRUD OPERATIONS =====");

    // Create a new category
//    Category newCategory = new Category();
//    newCategory.setName("TEST");
//    newCategory.setDescription("TEST");
//    categoryDao.create(newCategory);

    // Find category by id
//    Category foundCategory = categoryDao.findById(1L);
//    System.out.println("found Category : " + foundCategory);
//    foundCategory.setName("amine");

//    categoryDao.update(foundCategory.getId(), foundCategory);
//    Category foundCategory1 = categoryDao.findById(1L);
//    System.out.println("Updated Category: " + foundCategory1);

    // Find all categories
//    System.out.println("All Categories:");
//    categoryDao.findAll().forEach(System.out::println);

    // Delete category
//    categoryDao.delete(2L);

    // Test CRUD operations for Product
//    System.out.println("===== PRODUCT CRUD OPERATIONS =====");

//    // Create a new product
//    Product newProduct = new Product();
//    newProduct.setDescription("Smartphone");
//    newProduct.setPrice(599.99);
//    newProduct.setQuantity(100L);
//    newProduct.setSdr(1L);
//    newProduct.setCategory(newCategory); // Assuming the newCategory is now persisted
//    productDao.create(newProduct);

    // Find product by id
//    Product foundProduct = productDao.findById(1L);
//    System.out.println("Found Product: " + foundProduct);

    // Update product
//    foundProduct.setPrice(549.99);
//    productDao.update(foundProduct.getId(), foundProduct);
//    System.out.println("Updated Product: " + foundProduct);

    // Find all products
//    System.out.println("All Products:");
//    productDao.findAll().forEach(System.out::println);

    // Delete product
//    productDao.delete(foundProduct.getId());
  }
}
