package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    // test if home page shows correctly
    @Test
    void testHomePage() {
        HomeController homeController = new HomeController();
        String viewName = homeController.showHomePage();
        assertEquals("HomePage", viewName);
    }
}