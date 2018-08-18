package fr.adaming.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.service.ICommandeService;

@SuppressWarnings("serial")
@ManagedBean(name = "cmdMB")
@RequestScoped
public class CommandeManagedBean implements Serializable {

	private Commande commande;

	private Client client;

	private List<Commande> listeAllCommandes;

	private List<Commande> listeGetCommande;

	private List<LigneCommande> listePanier = new ArrayList<>();

	
	
	@ManagedProperty(value = "#{cmdService}")
	private ICommandeService commandeService;

	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	/**
	 * Constructeur vide
	 */
	public CommandeManagedBean() {
		super();
		this.commande = new Commande();
	}

	/**
	 * m�thode qui permet de charger la liste au moment de l'instanciation du
	 * managedBean
	 */
	@PostConstruct
	public void init() {
		//permet de r�cup�rer la liste des commandes
		this.listeAllCommandes = commandeService.getAllCommandes();

		//pour r�cup�rer les informations du panier client, pour passer une commande
		// r�cup�re le panier dans la session
		Panier panSession = (Panier) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("panierClient");

		// on r�cupere la session du panier et on verifie qu'elle ne soit pas
		// vide
		if (panSession != null) {

			// on recupere la liste de commande du panier et on verifie qu'elle
			// ne soit pas vide
			if (panSession.getListeCommande() != null) {

				// on stocke la nouvelle liste dans la nouvelle
				for (LigneCommande elem : panSession.getListeCommande()) {
					this.listePanier.add(elem);

				}
			}
		}
	}

	// Getters et setters
	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Commande> getListeAllCommandes() {
		return listeAllCommandes;
	}

	public void setListeAllCommandes(List<Commande> listeAllCommandes) {
		this.listeAllCommandes = listeAllCommandes;
	}

	public List<Commande> getListeGetCommande() {
		return listeGetCommande;
	}

	public void setListeGetCommande(List<Commande> listeGetCommande) {
		this.listeGetCommande = listeGetCommande;
	}

	public List<LigneCommande> getListePanier() {
		return listePanier;
	}

	public void setListePanier(List<LigneCommande> listePanier) {
		this.listePanier = listePanier;
	}

	/**
	 * M�thode qui permet de rechercher une commande � partir de son id
	 * 
	 * @return
	 */
	public String getCommandeById() {

		Commande cmdOut = commandeService.getCommandeById(this.commande);

		if (cmdOut != null) {
			this.commande = cmdOut;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La commande recherch�e n'existe pas"));
		}

		return "";
	}

	/**
	 * Si connect� en temps que client : permet de rechercher la liste de ses
	 * commandes Si connect� en temps qu'admin : permet de rechercher une
	 * commande par son id Si pas connect� : demande de se connecter
	 * 
	 * @return
	 */
	public String getCommandeByIdOrClient() {

		// On v�rifie si un client est connect� pour faire la recherche par son
		// id client
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clSession") != null) {
			// on charge les informations du client de la session dans le cl du
			// managedBean
			this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("clSession");

			// On rechercher des commande par leur id ou par l'id du client
			// connect�
			this.listeGetCommande = commandeService.getCommandeByIdOrClient(this.commande, this.client);

			if (this.listeGetCommande.size() <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Il n'y a pas de commande associ� � cette id ou a votre compte client"));
			}

			// Si l'admin est connect� on peut faire la rechercher seulement par
			// id
		} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("adminSession") != null) {

			Commande cmdOut = commandeService.getCommandeById(this.commande);

			if (cmdOut != null) {
				this.commande = cmdOut;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Il n'y a pas de commande associ� � cette id"));
			}

		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Veuillez vous connecter pour consulter vos commandes"));
		}

		return "";
	}

	/**
	 * M�thode qui permet de cr�er une commande par un client connect�
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
						new FacesMessage("La commande a �t� cr�e avec succ�s"));
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
								new FacesMessage("votre commande a bien �t� supprim�"));
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
					new FacesMessage("vous n'�tes pas connect� � votre espace Client"));
		}

		return "";
	}

	/**
	 * M�thode qui permet de cr�er la commande avec les produits et les
	 * quantit�s associ�. 
	 * 
	 * @return
	 */
	public String passerCommande() {

		// R�cup�rer le client dans la session
		Client clOut = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clSession");

		if (clOut != null) {

			// cr�er une commande avec le panier qui est dans la session
			int verif = commandeService.passerCommande(this.listePanier, clOut);

			switch (verif) {
			case 0:
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						"Erreur type 1 : La cr�ation de la commande n'a pas fonctionn�, merci de r�essayer"));
				return "";

			case 1:

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La commande est cr��e"));

				// vider le panier apr�s avoir pass� la commande
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("panierClient", null);

				this.listePanier = null;
				return "";

			case 2:
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						"Erreur type 2 : Une erreur s'est produit lors de la cr�ation de la commande, merci de r�essayer"));
				return "";

			case 3:
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						"Erreur type 3 : une erreur s'est produite lors de la cr�ation de la commande, contactez un administrateur."));
				return "";
			default:
				return "";
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Veuillez vous connecter pour passer commande"));
		}

		return "";
	}

	
	
	
	
	
}
