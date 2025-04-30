package com.daniellaw.recrutamento.patentes.repository;

import com.daniellaw.recrutamento.patentes.entities.Applicant;
import com.daniellaw.recrutamento.patentes.entities.Patent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
