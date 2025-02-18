package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    private Product testProduct1;
    private Product testProduct2;


    @BeforeEach
    void setUp() {
        testProduct1 = new Product();
        testProduct1.setProductName("Milo Dinosaur");
        testProduct1.setProductQuantity(50);
        testProduct1.setProductId("R44444WR");

        testProduct2 = new Product();
        testProduct2.setProductName("Marie Biscuit Tea");
        testProduct2.setProductQuantity(25);
        testProduct2.setProductId("M4444R13");
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productRepository.create(product)).thenReturn(product);
        Product createdProduct = productService.create(product);

        assertEquals(product, createdProduct);
        verify(productRepository).create(product);
    }

    @Test
    void testFindById() {
        when(productRepository.findById("R44444WR")).thenReturn(testProduct1);
        Product result = productService.findById("R44444WR");

        assertEquals("R44444WR", result.getProductId());
        verify(productRepository).findById("R44444WR");
    }

    @Test
    void testFindAll() {
        List<Product> productList = Arrays.asList(testProduct1, testProduct2);
        Iterator<Product> iterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);
        List<Product> allProducts = productService.findAll();

        assertEquals(2, allProducts.size());
        assertEquals("Milo Dinosaur", allProducts.get(0).getProductName());
        assertEquals("Marie Biscuit Tea", allProducts.get(1).getProductName());
        verify(productRepository).findAll();
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.update(testProduct1)).thenReturn(testProduct1);
        Product updatedProduct = productService.update(testProduct1);

        assertEquals("R44444WR", updatedProduct.getProductId());
        verify(productRepository).update(testProduct1);
    }

    @Test
    void testDeleteProduct() {
        productService.delete("R44444WR");

        verify(productRepository).delete("R44444WR");
    }
}