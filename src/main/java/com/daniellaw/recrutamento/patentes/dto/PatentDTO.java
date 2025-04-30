package com.daniellaw.recrutamento.patentes.dto;

import com.daniellaw.recrutamento.patentes.entities.Applicant;
import com.daniellaw.recrutamento.patentes.entities.Patent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatentDTO {
    private Long id;
    private String publicationNumber;
    private String internationalApplicationNumber;
    private LocalDate publicationDate;
    private List<ApplicantDTO> applicantsDTO;
    private String title;

    public PatentDTO() {}

    public PatentDTO(Long id, String publicationNumber, String internationalApplicationNumber, LocalDate publicationDate, List<ApplicantDTO> applicantsDTO, String title) {
        this.id = id;
        this.publicationNumber = publicationNumber;
        this.internationalApplicationNumber = internationalApplicationNumber;
        this.publicationDate = publicationDate;
        this.applicantsDTO = applicantsDTO;
        this.title = title;
    }

    public PatentDTO(Patent entity) {
        this.id = entity.getId();
        this.publicationNumber = entity.getPublicationNumber();
        this.internationalApplicationNumber = entity.getInternationalAppNumber();
        this.publicationDate = entity.getPublicationDate();
        if (entity.getApplicants() != null) {
            this.applicantsDTO = new ArrayList<>();
            for (Applicant applicant : entity.getApplicants()) {
                this.applicantsDTO.add(new ApplicantDTO(applicant));
            }
        }
        this.title = entity.getTitle();
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicationNumber() {
        return publicationNumber;
    }

    public void setPublicationNumber(String publicationNumber) {
        this.publicationNumber = publicationNumber;
    }

    public String getInternationalApplicationNumber() {
        return internationalApplicationNumber;
    }

    public void setInternationalApplicationNumber(String internationalApplicationNumber) {
        this.internationalApplicationNumber = internationalApplicationNumber;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<ApplicantDTO> getApplicants() {
        return applicantsDTO;
    }

    public void setApplicants(List<ApplicantDTO> applicantsDTO) {
        this.applicantsDTO = applicantsDTO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PatentDTO{" +
                "id=" + id +
                ", publicationNumber='" + publicationNumber + '\'' +
                ", internationalApplicationNumber='" + internationalApplicationNumber + '\'' +
                ", publicationDate=" + publicationDate +
                ", applicantsDTO=" + applicantsDTO +
                ", title='" + title + '\'' +
                '}';
    }
}