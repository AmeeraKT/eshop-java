package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

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
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(),'Submit')]"));

        nameInput.sendKeys("Kriuk Kriuk Crickets");
        quantityInput.sendKeys("45");

        submitButton.click();

        // direct to product list page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));
        assertTrue(driver.getCurrentUrl().contains("/product/list"));

        // check for table
        WebElement productTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("table")));
        assertNotNull(productTable, "Product table should be present on the page");

        // check product
        WebElement productRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='row']/div[@class='cell' and contains(text(),'Kriuk Kriuk Crickets')]")
        ));
        assertNotNull(productRow, "Product name should appear in the table");

        // check quantity
        WebElement quantityRow = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='row']/div[@class='cell' and contains(text(),'45')]")
        ));
        assertNotNull(quantityRow, "Product quantity should be visible");

        String pageContent = productTable.getText();
        assertTrue(pageContent.contains("Kriuk Kriuk Crickets"));
        assertTrue(pageContent.contains("45"));
    }
}