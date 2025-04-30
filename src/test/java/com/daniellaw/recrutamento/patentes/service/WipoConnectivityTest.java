package com.daniellaw.recrutamento.patentes.service;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WipoConnectivityTest {

    @Test
    void testConectividadeComURL() throws Exception {
        String docId = "WO2023001234"; // use um docId válido
        String urlStr = "https://patentscope.wipo.int/search/pt/detail.jsf?docId=" + docId;

        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "Esperado HTTP 200 OK da URL");
    }
}
