package com.daniellaw.recrutamento.patentes.service;
import com.daniellaw.recrutamento.patentes.dto.PatentDTO;
import com.daniellaw.recrutamento.patentes.entities.Applicant;
import com.daniellaw.recrutamento.patentes.entities.Patent;
import com.daniellaw.recrutamento.patentes.repository.ApplicantRepository;
import com.daniellaw.recrutamento.patentes.repository.PatentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatentService {

    private final PatentRepository repository;
    private final ApplicantRepository applicantRepository;
    private final WipoScraper scraper;
    private final Parser parser;

    public PatentService(PatentRepository repository, ApplicantRepository applicantRepository, WipoScraper scraper, Parser parser) {
        this.repository = repository;
        this.applicantRepository = applicantRepository;
        this.scraper = scraper;
        this.parser = parser;
    }

    public PatentDTO searchPatent(String numeroProcesso) {
        String html = scraper.fetchPatentDetailsHtml(numeroProcesso);
        return parser.parseHtmlToPatent(html);
    }

    @Transactional
    public PatentDTO insert(PatentDTO patentDTO) {
        Patent patent = convertDtoToEntity(patentDTO);
        for (Applicant applicant : patent.getApplicants()) {
            applicantRepository.save(applicant);
        }
        patent = repository.save(patent);
        return new PatentDTO(patent);
    }

    public List<PatentDTO> filterPatents(String numeroProcesso, String nomeRequerente) {
        List<Patent> patents = repository.filterPatents(numeroProcesso, nomeRequerente);
        return patents.stream()
                .map(PatentDTO::new)
                .collect(Collectors.toList());
    }

    private Patent convertDtoToEntity(PatentDTO dto) {
        Patent patent = new Patent();
        patent.setPublicationNumber(dto.getPublicationNumber());
        patent.setInternationalAppNumber(dto.getInternationalApplicationNumber());
        patent.setPublicationDate(dto.getPublicationDate());
        patent.setTitle(dto.getTitle());

        List<Applicant> applicants = dto.getApplicants().stream()
                .map(Applicant::new)
                .collect(Collectors.toList());
        patent.getApplicants().addAll(applicants);

        return patent;
    }
}
