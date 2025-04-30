package com.daniellaw.recrutamento.patentes.repository;

import com.daniellaw.recrutamento.patentes.entities.Patent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatentRepository extends JpaRepository<Patent, Long> {

    @Query("SELECT DISTINCT p FROM Patent p " +
            "JOIN FETCH p.applicants a " +
            "WHERE (:numeroProcesso IS NULL OR " +
            "LOWER(REPLACE(p.publicationNumber, '/', '')) LIKE LOWER(CONCAT('%', :numeroProcesso, '%'))) " +
            "AND (:nomeRequerente IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :nomeRequerente, '%')))")
    List<Patent> filterPatents(@Param("numeroProcesso") String numeroProcesso,
                               @Param("nomeRequerente") String nomeRequerente);
}
