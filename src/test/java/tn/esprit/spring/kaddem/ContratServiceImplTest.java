package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.util.*;

public class ContratServiceImplTest {

    @InjectMocks
    ContratServiceImpl contratService;  // Service under test

    @Mock
    ContratRepository contratRepository;

    @Mock
    EtudiantRepository etudiantRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testRetrieveAllContrats() {
        // Arrange
        List<Contrat> contrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratRepository.findAll()).thenReturn(contrats);

        // Act
        List<Contrat> result = contratService.retrieveAllContrats();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testAddContrat() {
        // Arrange
        Contrat contrat = new Contrat();
        when(contratRepository.save(contrat)).thenReturn(contrat);

        // Act
        Contrat result = contratService.addContrat(contrat);

        // Assert
        assertNotNull(result);
        verify(contratRepository).save(contrat);
    }

    @Test
    public void testAffectContratToEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(new HashSet<>());  // Initialize with an empty set of contracts

        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);

        when(etudiantRepository.findByNomEAndPrenomE("John", "Doe")).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);

        // Act
        Contrat result = contratService.affectContratToEtudiant(1, "John", "Doe");

        // Assert
        assertNotNull(result);
        assertEquals(etudiant, result.getEtudiant());
        verify(contratRepository).save(contrat);  // Verify save operation
    }

}