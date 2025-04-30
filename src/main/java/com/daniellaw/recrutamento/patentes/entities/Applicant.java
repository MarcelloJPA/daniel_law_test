package com.daniellaw.recrutamento.patentes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import com.daniellaw.recrutamento.patentes.dto.ApplicantDTO;

@Entity
@Table(name = "tb_applicant")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Applicant() {}

    public Applicant(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Applicant(ApplicantDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
