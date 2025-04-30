package com.daniellaw.recrutamento.patentes.service;

import com.daniellaw.recrutamento.patentes.dto.ApplicantDTO;
import com.daniellaw.recrutamento.patentes.dto.PatentDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Parser {
    private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final String SELECTOR_PUBLICATION_NUMBER    = "span:containsOwn(Número da publicação) + span span";
    private static final String SELECTOR_INTERNATIONAL_NUMBER  = "span:containsOwn(№ do pedido internacional) + span";
    private static final String SELECTOR_PUBLICATION_DATE      = "span:containsOwn(Data de publicação) + span";
    private static final String SELECTOR_APPLICANTS            = "span:containsOwn(Requerentes) + span ul.biblio-person-list li span.biblio-person-list--name";
    private static final String SELECTOR_TITLE_EN              = "div.patent-title div:has(b:contains(EN)) span.needTranslation-title";

    public PatentDTO parseHtmlToPatent(String html) {
        Document document = Jsoup.parse(html);
        PatentDTO dto = new PatentDTO();

        dto.setPublicationNumber(extractText(document, SELECTOR_PUBLICATION_NUMBER));
        dto.setInternationalApplicationNumber(extractText(document, SELECTOR_INTERNATIONAL_NUMBER));
        dto.setPublicationDate(extractDate(document, SELECTOR_PUBLICATION_DATE));
        dto.setApplicants(extractApplicants(document));
        dto.setTitle(extractText(document, SELECTOR_TITLE_EN));

        return dto;
    }

    private String extractText(Document document, String selector) {
        Element element = document.selectFirst(selector);
        if (element == null) {
            LOGGER.warn("Elemento não encontrado para o seletor: {}", selector);
            return null;
        }
        return element.text().trim();
    }

    private LocalDate extractDate(Document document, String selector) {
        String text = extractText(document, selector);
        if (text == null) {
            return null;
        }
        try {
            return LocalDate.parse(text, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            LOGGER.error("Falha ao converter data '{}' usando o padrão {}", text, DATE_FORMATTER, e);
            return null;
        }
    }

    private List<ApplicantDTO> extractApplicants(Document document) {
        return document.select(SELECTOR_APPLICANTS).stream()
                .map(element -> {
                    ApplicantDTO applicantDTO = new ApplicantDTO();
                    applicantDTO.setName(element.text().trim());
                    return applicantDTO;
                })
                .collect(Collectors.toList());
    }
}