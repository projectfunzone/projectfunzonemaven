package fr.adaming.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.service.ICommandeService;

@SuppressWarnings("serial")
@ManagedBean(name = "cmdMB")
@RequestScoped
public class CommandeManagedBean implements Serializable {

	@ManagedProperty(value = "#{cmdService}")
	private ICommandeService commandeService;

	private Commande commande;

	private Client client;

	private List<Commande> listeAllCommandes;

	private List<Commande> listeGetCommande;

	/**
	 * Constructeur vide
	 */
	public CommandeManagedBean() {
		super();
		this.commande = new Commande();
	}

	/**
	 * méthode qui permet de charger la liste au moment de l'instanciation du
	 * managedBean
	 */
	@PostConstruct
	public void init() {
		this.listeAllCommandes = commandeService.getAllCommandes();

	}

	// Getters et setters
	/**
	 * @return the commandeService
	 */
	public ICommandeService getCommandeService() {
		return commandeService;
	}

	/**
	 * @param commandeService
	 *            the commandeService to set
	 */
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	/**
	 * @return the commande
	 */
	public Commande getCommande() {
		return commande;
	}

	/**
	 * @param commande
	 *            the commande to set
	 */
	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the listeAllCommandes
	 */
	public List<Commande> getListeAllCommandes() {
		return listeAllCommandes;
	}

	/**
	 * @param listeAllCommandes
	 *            the listeAllCommandes to set
	 */
	public void setListeAllCommandes(List<Commande> listeAllCommandes) {
		this.listeAllCommandes = listeAllCommandes;
	}

	/**
	 * @return the listeGetCommande
	 */
	public List<Commande> getListeGetCommande() {
		return listeGetCommande;
	}

	/**
	 * @param listeGetCommande
	 *            the listeGetCommande to set
	 */
	public void setListeGetCommande(List<Commande> listeGetCommande) {
		this.listeGetCommande = listeGetCommande;
	}

	/**
	 * Méthode qui permet de rechercher une commande à partir de son id
	 * 
	 * @return
	 */
	public String getCommandeById() {

		Commande cmdOut = commandeService.getCommandeById(this.commande);

		if (cmdOut != null) {
			this.commande = cmdOut;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La commande recherchée n'existe pas"));
		}

		return "";
	}

	/**
	 * Si connecté en temps que client : permet de rechercher la liste de ses
	 * commandes 
	 * Si connecté en temps qu'admin : permet de rechercher une
	 * commande par son id 
	 * Si pas connecté : demande de se connecter
	 * 
	 * @return
	 */
	public String getCommandeByIdOrClient() {

		// On vérifie si un client est connecté pour faire la recherche par son
		// id client
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clSession") != null) {
			// on charge les informations du client de la session dans le cl du
			// managedBean
			this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("clSession");

			// On rechercher des commande par leur id ou par l'id du client
			// connecté
			this.listeGetCommande = commandeService.getCommandeByIdOrClient(this.commande, this.client);

			if (this.listeGetCommande.size() <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Il n'y a pas de commande associé à cette id ou a votre compte client"));
			}

			// Si l'admin est connecté on peut faire la rechercher seulement par
			// id
		} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("adminSession") != null) {

			Commande cmdOut = commandeService.getCommandeById(this.commande);

			if (cmdOut != null) {
				this.commande = cmdOut;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Il n'y a pas de commande associé à cette id"));
			}

		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Veuillez vous connecter pour consulter vos commandes"));
		}

		return "";
	}

	/**
	 * Méthode qui permet de créer une commande par un client connecté
	 * 
	 * @return
	 */
	public String addCommande() {

		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clSession") != null) {

			this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("clSession");
			Commande cmdOut = commandeService.addCommande(this.commande, this.client);

			if (cmdOut != null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("La commande a été crée avec succès"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						"une erreur s'est produite lors du passage de la commande, consulter un admin"));
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Veuillez vous connecter pour passer commandes"));
		}
		return "";
	}

	public String deleteCommande() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clSession") != null) {

			this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("clSession");

			Commande cmdOut = commandeService.getCommandeById(this.commande);

			if (cmdOut != null) {
				if (cmdOut.getCl().getIdClient() != this.client.getIdClient()) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Cette commande n'existe pas dans votre liste de commande"));
				} else {

					int verif = commandeService.deleteCommande(this.commande, this.client);

					if (verif != 0) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage("votre commande a bien été supprimé"));
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
								"une erreur s'est produite lors de la suppresion de la commande, consulter un admin"));
					}
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("la commande que vous voulez supprimer n'existe pas"));
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("vous n'êtes pas connecté à votre espace Client"));
		}

		return "";
	}

}
