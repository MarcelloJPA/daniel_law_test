package com.daniellaw.recrutamento.patentes.controller;

import com.daniellaw.recrutamento.patentes.dto.PatentDTO;
import com.daniellaw.recrutamento.patentes.service.PatentService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/patents")
public class PatentController {

    private final PatentService service;

    public PatentController(PatentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "buscar_patent";  // view stays unchanged
    }

    @GetMapping("/filtrarPatents")
    public String filterPatentsView() {
        return "filtrarPatents";  // view stays unchanged
    }

    @PostMapping("/search")
    public ResponseEntity<PatentDTO> searchPatent(@RequestParam("numeroProcesso") @NotBlank String processNumber) {
        PatentDTO dto = service.searchPatent(processNumber);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> savePatent(@RequestBody PatentDTO patentDTO) {
        PatentDTO saved = service.insert(patentDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/filter")
    @ResponseBody
    public List<PatentDTO> filterPatents(
            @RequestParam(required = false, name = "numeroProcesso") String processNumber,
            @RequestParam(required = false, name = "nomeRequerente") String applicantName) {

        return service.filterPatents(processNumber, applicantName);
    }
}
