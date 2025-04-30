package com.daniellaw.recrutamento.patentes.service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.fail;


import static org.junit.jupiter.api.Assertions.assertTrue;


public class WipoResponseValidationTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER); // Para não esperar tudo
        options.addArguments("--headless"); // Roda sem abrir a janela
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testHtmlContemIdDetailMainForm() {
        String numeroProcesso = "WO2023001234";
        String url = "https://patentscope.wipo.int/search/pt/detail.jsf?docId=" + numeroProcesso;
        driver.get(url);

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("detailMainForm")));
            String pageSource = driver.getPageSource();
            assertTrue(pageSource.contains("detailMainForm"), "Elemento 'detailMainForm' não encontrado na página.");
        } catch (TimeoutException e) {
            // Caso o elemento não seja encontrado, considere a lógica de falha do seu teste
            fail("Tempo esgotado para localizar o elemento 'detailMainForm'. " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
