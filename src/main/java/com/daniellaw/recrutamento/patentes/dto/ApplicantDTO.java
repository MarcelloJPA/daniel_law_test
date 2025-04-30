package com.daniellaw.recrutamento.patentes.dto;

import com.daniellaw.recrutamento.patentes.entities.Applicant;

public class ApplicantDTO {
    private Long id;
    private String name;

    public ApplicantDTO() {}

    public ApplicantDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ApplicantDTO(Applicant entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "ApplicantDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
