package id.ac.ui.cs.advprog.eshop.controller;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    private Product testProduct3;
    private Model model;

    @BeforeEach
    void setUp() {
        testProduct3 = new Product();
        testProduct3.setProductName("Ovaltine Crunch Bread");
        testProduct3.setProductQuantity(75);
        testProduct3.setProductId("7UMM7");

        model = mock(Model.class);
    }

    @Test
    void testCreateProductPage() {
        String newProduct = controller.createProductPage(model);

        assertEquals("CreateProduct", newProduct);
        verify(model).addAttribute(eq("product"), any(Product.class));
    }

    @Test
    void testAddProductPost() {
        String result = controller.createProductPost(testProduct3, model);

        assertEquals("redirect:list", result);
        verify(service).create(testProduct3);
    }

    @Test
    void testListProductPage() {
        List<Product> productList = Arrays.asList(testProduct3);
        when(service.findAll()).thenReturn(productList);

        String result = controller.listProductPage(model);
        assertEquals("ProductList", result);
        verify(model).addAttribute("products", productList);
    }

    @Test
    void testEditProductPage() {
        when(service.findById("7UMM7")).thenReturn(testProduct3);
        String result = controller.editProductPage("7UMM7", model);

        assertEquals("EditProduct", result);
        verify(model).addAttribute("product", testProduct3);
        verify(service).findById("7UMM7");
    }

    @Test
    void testEditProductPageNull() {
        when(service.findById("H3LL0W0R7D")).thenReturn(null);
        String result = controller.editProductPage("H3LL0W0R7D", model);

        assertEquals("redirect:/product/list", result);
    }

    @Test
    void testEditProductPost() {
        String result = controller.editProductPost(testProduct3, model);

        assertEquals("redirect:/product/list", result);
        verify(service).update(testProduct3);
    }
    @Test
    void testDeleteProduct() {
        String result = controller.deleteProduct("7UMM7");

        assertEquals("redirect:/product/list", result);
        verify(service).delete("7UMM7");
    }
}