package com.daniellaw.recrutamento.patentes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_patent")
public class Patent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publicationNumber;
    private String internationalAppNumber;
    private LocalDate publicationDate;

    @ManyToMany
    @JoinTable(
            name = "tb_patent_applicant",
            joinColumns = @JoinColumn(name = "patent_id"),
            inverseJoinColumns = @JoinColumn(name = "applicant_id")
    )
    private List<Applicant> applicants = new ArrayList<>();

    private String title;

    public Patent() {}

    public Patent(Long id, String publicationNumber, String internationalAppNumber, LocalDate publicationDate, List<Applicant> applicants, String title) {
        this.id = id;
        this.publicationNumber = publicationNumber;
        this.internationalAppNumber = internationalAppNumber;
        this.publicationDate = publicationDate;
        this.applicants = applicants;
        this.title = title;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPublicationNumber() { return publicationNumber; }
    public void setPublicationNumber(String publicationNumber) { this.publicationNumber = publicationNumber; }

    public String getInternationalAppNumber() { return internationalAppNumber; }
    public void setInternationalAppNumber(String internationalAppNumber) { this.internationalAppNumber = internationalAppNumber; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public List<Applicant> getApplicants() { return applicants; }
    public void setApplicants(List<Applicant> applicants) { this.applicants = applicants; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
