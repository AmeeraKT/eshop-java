package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

// TEST CLASSES OUTLINE
// 1) Product Repository Tests
//      a) Create product tests
//      b) Edit product tests
//      c) Delete product tests

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Nested
    @DisplayName("Create Product Tests")
    class CreateProductTests {
        @Test
        void testCreateAndFind() {

            Product product = new Product();
            product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
            product.setProductName("Sampo Cap Bambang");
            product.setProductQuantity(100);
            productRepository.create(product);

            Iterator<Product> productIterator = productRepository.findAll();
            assertTrue(productIterator.hasNext());
            Product savedProduct = productIterator.next();
            assertEquals(product.getProductId(), savedProduct.getProductId());
            assertEquals(product.getProductName(), savedProduct.getProductName());
            assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());

        }

        @Test
        void testFindAllIfEmpty() {
            Iterator<Product> productIterator = productRepository.findAll();
            assertFalse(productIterator.hasNext());
        }

        @Test
        void testFindAllIfMoreThanOneProduct() {

            Product product1 = new Product();
            product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
            product1.setProductName("Sampo Cap Bambang");
            product1.setProductQuantity(100);
            productRepository.create(product1);

            Product product2 = new Product();
            product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
            product2.setProductName("Sampo Cap Usep");
            product2.setProductQuantity(50);
            productRepository.create(product2);

            Iterator<Product> productIterator = productRepository.findAll();
            assertTrue(productIterator.hasNext());
            Product savedProduct = productIterator.next();
            assertEquals(product1.getProductId(), savedProduct.getProductId());
            savedProduct = productIterator.next();
            assertEquals(product2.getProductId(), savedProduct.getProductId());
            assertFalse(productIterator.hasNext());
        }
    }


    @Nested
    @DisplayName("Edit Product Tests")
    class EditProductTests {

        @Test
        void testEditExistingProduct() {
            Product product = new Product();
            product.setProductId("eb108e9f-1c39-460e-8860-22af6af63bd6");
            product.setProductName("Butter Almond Croissant");
            product.setProductQuantity(50);
            Product createdProduct = productRepository.create(product);

            Product updatedProduct = new Product();
            updatedProduct.setProductId(createdProduct.getProductId());
            updatedProduct.setProductName("Strawberry Shortcake");
            updatedProduct.setProductQuantity(75);

            Product result = productRepository.update(updatedProduct);
            assertNotNull(result);
            assertEquals(updatedProduct.getProductName(), result.getProductName());
            assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
        }

        @Test
        void testEditNonexistingProduct() {
            Product updatedProduct = new Product();
            updatedProduct.setProductId("SuperFakeId");
            updatedProduct.setProductName("Fake Product");
            updatedProduct.setProductQuantity(1);

            Product result = productRepository.update(updatedProduct);
            assertNull(result);
        }

        @Test
        void testEditSecondProductInListOfThree() {
            Product product1 = new Product();
            product1.setProductId("FirstProduct");
            product1.setProductName("Flying Fork");
            product1.setProductQuantity(10);
            productRepository.create(product1);

            Product product2 = new Product();
            product2.setProductId("SecondProduct");
            product2.setProductName("Sambel Cap Kambing");
            product2.setProductQuantity(15);
            productRepository.create(product2);

            Product product3 = new Product();
            product3.setProductId("ThirdProduct");
            product3.setProductName("Cute Cat");
            product3.setProductQuantity(20);
            productRepository.create(product3);

            assertNotNull(productRepository.findById("FirstProduct"));
            assertNotNull(productRepository.findById("SecondProduct"));
            assertNotNull(productRepository.findById("ThirdProduct"));

            Product updatedProduct = new Product();
            updatedProduct.setProductId("SecondProduct");
            updatedProduct.setProductName("Sambel Cap Belsam");
            updatedProduct.setProductQuantity(30);

            Product result = productRepository.update(updatedProduct);
            assertNotNull(result);
            assertEquals(updatedProduct.getProductId(), result.getProductId());
            assertEquals(updatedProduct.getProductName(), result.getProductName());
            assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());

            // Check 1st & 3rd products are the same
            Product unchangedProduct1 = productRepository.findById("FirstProduct");
            Product unchangedProduct3 = productRepository.findById("ThirdProduct");

            assertNotNull(unchangedProduct1);
            assertEquals("Flying Fork", unchangedProduct1.getProductName());
            assertEquals(10, unchangedProduct1.getProductQuantity());

            assertNotNull(unchangedProduct3);
            assertEquals("Cute Cat", unchangedProduct3.getProductName());
            assertEquals(20, unchangedProduct3.getProductQuantity());
        }
    }



    @Nested
    @DisplayName("Delete Product Tests")
    class DeleteProductTests {

        @Test
        void testDeleteExistingProduct() {
            Product product = new Product();
            product.setProductId("RealId123");
            product.setProductName("Pandan Cheese Cake");
            product.setProductQuantity(25);
            productRepository.create(product);

            Product deletedProduct = productRepository.delete(product.getProductId());
            assertNotNull(deletedProduct);
            assertEquals(product.getProductId(), deletedProduct.getProductId());

            Product foundProduct = productRepository.findById(product.getProductId());
            assertNull(foundProduct);
        }

        @Test
        void testDeleteNonexistingProduct() {
            Product deletedProduct = productRepository.delete("SuperFakeId");

            assertNull(deletedProduct);

            Product foundProduct = productRepository.findById("SuperFakeId");
            assertNull(foundProduct);
        }


        @Test
        void testDeleteSecondProductInListOfThree() {
            Product product1 = new Product();
            product1.setProductId("FirstProduct");
            product1.setProductName("Fork1");
            product1.setProductQuantity(10);
            productRepository.create(product1);

            Product product2 = new Product();
            product2.setProductId("SecondProduct");
            product2.setProductName("Fork2");
            product2.setProductQuantity(15);
            productRepository.create(product2);

            Product product3 = new Product();
            product3.setProductId("ThirdProduct");
            product3.setProductName("Fork3");
            product3.setProductQuantity(20);
            productRepository.create(product3);

            assertNotNull(productRepository.findById("FirstProduct"));
            assertNotNull(productRepository.findById("SecondProduct"));
            assertNotNull(productRepository.findById("ThirdProduct"));

            Product deletedProduct = productRepository.delete("SecondProduct");
            assertNotNull(deletedProduct);
            assertEquals("SecondProduct", deletedProduct.getProductId());

            assertNotNull(productRepository.findById("FirstProduct"));
            assertNull(productRepository.findById("SecondProduct"));
            assertNotNull(productRepository.findById("ThirdProduct"));
        }
    }
}