package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import personnel.*;

class testEmploye
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

    @Test
    void changerNom() throws SauvegardeImpossible
    {
    	Employe employe = gestionPersonnel.addLigue("Escrime")
    			.addEmploye("Nom", "Prenom", "e@mail.com", "pass");
    	employe.setNom("NouveauNom");
    	assertEquals("NouveauNom", employe.getNom());
    }

    @Test
    void changerPrenom() throws SauvegardeImpossible
    {
    	Employe employe = gestionPersonnel.addLigue("Boxe")
    			.addEmploye("A", "B", "a@mail.com", "pass");
    	employe.setPrenom("NouveauPrenom");
    	assertEquals("NouveauPrenom", employe.getPrenom());
    }

    @Test
    void changerMail() throws SauvegardeImpossible
    {
    	Employe employe = gestionPersonnel.addLigue("Natation")
    			.addEmploye("A", "B", "a@mail.com", "pass");
    	employe.setMail("nouveau@mail.com");
    	assertEquals("nouveau@mail.com", employe.getMail());
    }

    @Test
    void changerPassword() throws SauvegardeImpossible
    {
    	Employe employe = gestionPersonnel.addLigue("Volley")
    			.addEmploye("A", "B", "a@mail.com", "pass");
    	employe.setPassword("nouveauPass");
    	assertTrue(employe.checkPassword("nouveauPass"));
    }

    @Test
    void impossibleDeSupprimerRoot()
    {
    	assertThrows(ImpossibleDeSupprimerRoot.class, () -> gestionPersonnel.getRoot().remove());
    }

    @Test
    void suppressionEmployeLeRetireDeLaLigue() throws SauvegardeImpossible
    {
    	Ligue ligue = gestionPersonnel.addLigue("Rugby");
    	Employe employe = ligue.addEmploye("A", "B", "a@mail.com", "pass");
    	employe.remove();
    	assertFalse(ligue.getEmployes().contains(employe));
    }

    @Test
    void suppressionAdministrateurRedonneLesDroitsAuRoot() throws SauvegardeImpossible
    {
    	Ligue ligue = gestionPersonnel.addLigue("Aviron");
    	Employe employe = ligue.addEmploye("A", "B", "a@mail.com", "pass");
    	ligue.setAdministrateur(employe);
    	employe.remove();
    	assertEquals(gestionPersonnel.getRoot(), ligue.getAdministrateur());
    }    
    
	@Test
	void dateArriveeParDefaut() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Tennis de table");
		Employe employe = ligue.addEmploye("Dupont", "Jean", "j.dupont@mail.com", "pass");
		assertEquals(LocalDate.now(), employe.getDateArrivee());
		assertNull(employe.getDateDepart());
	}

	@Test
	void dateDepartApresArrivee() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Pétanque");
		Employe employe = ligue.addEmploye("Martin", "Paul", "p.martin@mail.com", "pass");
		employe.setDateArrivee(LocalDate.of(2024, 1, 1));
		employe.setDateDepart(LocalDate.of(2024, 6, 1));
		assertEquals(LocalDate.of(2024, 6, 1), employe.getDateDepart());
	}

	@Test
	void dateDepartAvantArriveeLeveException() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Billard");
		Employe employe = ligue.addEmploye("Durand", "Marc", "m.durand@mail.com", "pass");
		employe.setDateArrivee(LocalDate.of(2024, 6, 1));
		assertThrows(DatesIncoherentes.class,
				() -> employe.setDateDepart(LocalDate.of(2024, 1, 1)));
	}

	@Test
	void dateArriveeNulleLeveException() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Judo");
		Employe employe = ligue.addEmploye("Petit", "Luc", "l.petit@mail.com", "pass");
		assertThrows(DatesIncoherentes.class, () -> employe.setDateArrivee(null));
	}
}