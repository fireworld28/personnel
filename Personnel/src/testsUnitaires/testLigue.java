package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}

	@Test
	void changerNomLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Hockey");
		ligue.setNom("Hockey sur gazon");
		assertEquals("Hockey sur gazon", ligue.getNom());
	}

	@Test
	void administrateurParDefautEstRoot() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Canoë");
		assertEquals(gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}

	@Test
	void changerAdministrateurAvecEmployeDeLaLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Kayak");
		Employe employe = ligue.addEmploye("A", "B", "a@mail.com", "pass");
		ligue.setAdministrateur(employe);
		assertEquals(employe, ligue.getAdministrateur());
	}

	@Test
	void changerAdministrateurAvecEmployeExterieurLeveException() throws SauvegardeImpossible
	{
		Ligue ligue1 = gestionPersonnel.addLigue("Judo 2");
		Ligue ligue2 = gestionPersonnel.addLigue("Karaté");
		Employe employeExterieur = ligue2.addEmploye("A", "B", "a@mail.com", "pass");
		assertThrows(DroitsInsuffisants.class, () -> ligue1.setAdministrateur(employeExterieur));
	}

	@Test
	void suppressionLigueLaRetireDeGestionPersonnel() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Tir à l'arc");
		ligue.remove();
		assertFalse(gestionPersonnel.getLigues().contains(ligue));
	}
	
}
