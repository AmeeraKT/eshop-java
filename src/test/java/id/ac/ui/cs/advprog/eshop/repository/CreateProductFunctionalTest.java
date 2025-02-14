package id.ac.ui.cs.advprog.eshop.repository;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CustomCreateProductTest {

    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + serverPort;
    }

    @Test
    void testCreateProduct(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Create Product')]"));

        // input product data
        nameInput.sendKeys("Kriuk Kriuk Crickets");
        quantityInput.sendKeys("45");

        // create product
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        assertTrue(driver.getCurrentUrl().contains("/product/list"));

        List<WebElement> tables = driver.findElements(By.tagName("table"));
        assertFalse(tables.isEmpty(), "Product table should be present on the page");

        WebElement productTable = tables.get(0);
        String pageContent = productTable.getText();
        assertNotNull(pageContent, "Table content should not be null");
        assertTrue(pageContent.contains("Kriuk Kriuk Crickets"));
        assertTrue(pageContent.contains("45"));
    }
}