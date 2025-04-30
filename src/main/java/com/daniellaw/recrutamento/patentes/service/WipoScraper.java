package com.daniellaw.recrutamento.patentes.service;
import com.daniellaw.recrutamento.patentes.service.exception.PatentNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class WipoScraper {

    private WebDriver driver;
    private WebDriverWait wait;

    @PostConstruct
    public void init() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER); // importante: EAGER para não esperar tudo
        options.addArguments("--headless"); // Roda sem abrir a janela
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @PreDestroy
    public void destroy() {
        if (driver != null) {
            driver.quit();
        }
    }

    public String fetchPatentDetailsHtml(String numeroProcesso) {
        String url = "https://patentscope.wipo.int/search/pt/detail.jsf?docId=" + numeroProcesso;
        driver.get(url);

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("detailMainForm")));
            return driver.getPageSource();
        } catch (TimeoutException e) {
            // Verifica se é erro de "patente não encontrada"
            if (driver.getPageSource().contains("Nenhum resultado encontrado")) {
                throw new PatentNotFoundException("Patente não encontrada: " + numeroProcesso);
            }
            throw new PatentNotFoundException("Erro ao acessar o WIPO.", e);
        }
    }
}
