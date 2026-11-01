package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import personnel.*;

class testEmploye
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

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