package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employé", "a",
				() -> 
				{
					ligue.addEmploye(getString("nom : "), 
						getString("prenom : "), getString("mail : "), 
						getString("password : "));
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}

	private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
		return new List<Employe>("Sélectionner un employé", "e",
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> menuEmploye(element)
				);
	}
	
	private Menu menuEmploye(final Employe employe)
	{
		Menu menu = new Menu("Employé " + employe.getNom() + " " + employe.getPrenom());
		menu.add(employeConsole.editerEmploye(employe));
		menu.add(supprimerEmploye(employe));
		menu.addBack("q");
		return menu;
	}
	
	private Option supprimerEmploye(final Employe employe)
	{
		return new Option("Supprimer l'employé", "s", () -> {employe.remove();});
	}
	
	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		return new List<Employe>("Changer l'administrateur", "a",
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) ->
				{
					try
					{
						ligue.setAdministrateur(element);
						System.out.println(element + " est maintenant l'administrateur de " + ligue.getNom());
					}
					catch (DroitsInsuffisants exception)
					{
						System.err.println("Cet employé n'appartient pas à la ligue.");
					}
				}
				);
	}

	private List<Employe> modifierEmploye(final Ligue ligue)
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}
	
	private LocalDate lireDate(String invite)
	{
		String saisie = getString(invite).trim();
		if (saisie.isEmpty())
			return null;
		return LocalDate.parse(saisie);
	}

	private Option changerDateArrivee(final Employe employe)
	{
		return new Option("Changer la date d'arrivée", "a", () ->
		{
			try
			{
				employe.setDateArrivee(lireDate("Nouvelle date d'arrivée (AAAA-MM-JJ) : "));
				System.out.println("Date d'arrivée enregistrée : " + employe.getDateArrivee());
			}
			catch (DateTimeParseException exception)
			{
				System.err.println("Format invalide, la date doit être saisie sous la forme AAAA-MM-JJ.");
			}
			catch (DatesIncoherentes exception)
			{
				System.err.println(exception.getMessage());
			}
		});
	}

	private Option changerDateDepart(final Employe employe)
	{
		return new Option("Changer la date de départ", "d", () ->
		{
			try
			{
				employe.setDateDepart(lireDate("Nouvelle date de départ (AAAA-MM-JJ, vide = aucune) : "));
				System.out.println("Date de départ enregistrée : " + employe.getDateDepart());
			}
			catch (DateTimeParseException exception)
			{
				System.err.println("Format invalide, la date doit être saisie sous la forme AAAA-MM-JJ.");
			}
			catch (DatesIncoherentes exception)
			{
				System.err.println(exception.getMessage());
			}
		});
	}
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
}
