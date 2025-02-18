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
class ProductServiceImplTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    private Product testProductOne;
    private Product testProductTwo;


    @BeforeEach
    void setUp() {
        testProductOne = new Product();
        testProductOne.setProductName("Milo Dinosaur");
        testProductOne.setProductQuantity(50);
        testProductOne.setProductId("R44444WR");

        testProductTwo = new Product();
        testProductTwo.setProductName("Marie Biscuit Tea");
        testProductTwo.setProductQuantity(25);
        testProductTwo.setProductId("M4444R13");
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
        when(productRepository.findById("R44444WR")).thenReturn(testProductOne);
        Product result = productService.findById("R44444WR");

        assertEquals("R44444WR", result.getProductId());
        verify(productRepository).findById("R44444WR");
    }

    @Test
    void testFindAll() {
        List<Product> productList = Arrays.asList(testProductOne, testProductTwo);
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
        when(productRepository.update(testProductOne)).thenReturn(testProductOne);
        Product updatedProduct = productService.update(testProductOne);

        assertEquals("R44444WR", updatedProduct.getProductId());
        verify(productRepository).update(testProductOne);
    }

    @Test
    void testDeleteProduct() {
        productService.delete("R44444WR");

        verify(productRepository).delete("R44444WR");
    }
}