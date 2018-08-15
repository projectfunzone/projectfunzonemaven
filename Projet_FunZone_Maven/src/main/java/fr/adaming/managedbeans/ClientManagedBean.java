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
import fr.adaming.service.IClientService;

/**
 * ManagedBean du client
 * 
 * @author Adaming
 *
 */
@SuppressWarnings("serial")
@ManagedBean(name = "clMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	/**
	 * Transformation de l'association UML en java, avec instanciation EJB
	 */
	@ManagedProperty(value = "#{clService}")
	private IClientService clientService;

	private Client client;

	private List<Client> listeAllClients;

	private List<Client> listeGetClient;

	/**
	 * permet de r�cup�rer la liste des clients au moment de l'instanciation du
	 * managedBean
	 */
	@PostConstruct
	public void init() {
		this.listeAllClients = clientService.getAllClients();
	}

	/**
	 * Constructeur vide avec instanciation d'un client
	 */
	public ClientManagedBean() {
		super();
		this.client = new Client();
	}

	/**
	 * @return the cl
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param cl
	 *            the cl to set
	 */
	public void setCl(Client client) {
		this.client = client;
	}

	/**
	 * @return the listeAllClient
	 */
	public List<Client> getListeAllClients() {
		return listeAllClients;
	}

	/**
	 * @return the listeGetClient
	 */
	public List<Client> getListeGetClient() {
		return listeGetClient;
	}

	/**
	 * @param listeGetClient
	 *            the listeGetClient to set
	 */
	public void setListeGetClient(List<Client> listeGetClient) {
		this.listeGetClient = listeGetClient;
	}

	/**
	 * @param listeAllClient
	 *            the listeAllClient to set
	 */
	public void setListeAllClients(List<Client> listeAllClients) {
		this.listeAllClients = listeAllClients;
	}

	public IClientService getClientService() {
		return clientService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * R�cup�rer un client par son id.
	 * 
	 * @return retourne la m�me page xhtml, afin d'afficher les informations sur
	 *         la page On v�rifie que le client r�cup�rer est diff�rent de null,
	 *         pour confirmer qu'il existe dans la base de donn�e.
	 */
	public String getClientById() {

		Client clOut = clientService.getClientById(this.client);

		if (clOut != null) {
			this.client = clOut;
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Il n'y a pas de client associ� � cet id"));
		}

		return "";

	}

	/**
	 * R�cup�rer un client par son id ou par son nom.
	 * 
	 * @return retourne la m�me page xhtml, afin d'afficher les informations sur
	 *         la page On v�rifie que la taille de la liste de r�sultat est >0,
	 *         pour confirmer qu'il y a bien des correspondances dans la base de
	 *         donn�e.
	 */
	public String getClientByIdNomMail() {
		this.listeGetClient = clientService.getClientByIdNomMail(this.client);

		if (this.listeGetClient.size() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					"Il n'y a pas de client correspondant � ce nom ou cet id ou ce mail dans la base de donn�e"));
		}

		return "";

	}

	/**
	 * Cr�er un client.
	 * 
	 * @return Met la liste des clients � jour dans la page
	 *         "listeAllClient.xhtml"
	 */
	public String addClient() {

		Client clOut = clientService.addClient(this.client);

		if (clOut != null) {
			this.listeAllClients = clientService.getAllClients();

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le compte a bien �t� cr�e"));
			return "clientAccueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produit"));
			return "clientAdd";
		}
	}

	/**
	 * Supprimer un client
	 * 
	 * @return Met la liste des clients � jour dans la page
	 *         "listeAllClient.xhtml" si le retour de la m�thode est =! 0, la
	 *         suppression a fonctionn� sinon, si ==0 c'est que la suppression
	 *         n'a pas fonctionner
	 */
	public String deleteClient() {
		int verif = clientService.deleteClient(this.client);

		if (verif != 0) {
			this.listeAllClients = clientService.getAllClients();

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le compte a bien �t� supprim�"));
			return "adminAccueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produit"));
			return "adminClientDelete";
		}

	}

	/**
	 * Modifier un client
	 * 
	 * @return Met la liste des clients � jour dans la page
	 *         "listeAllClient.xhtml" si le retour de la m�thode est =! 0, la
	 *         modif a fonctionn� sinon, si ==0 c'est que la modif n'a pas
	 *         fonctionner
	 */
	public String updateClient() {
		int verif = clientService.updateClient(this.client);

		if (verif != 0) {
			this.listeAllClients = clientService.getAllClients();

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modif a �t� prise en compte"));
			return "adminAccueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produit"));
			return "clientUpdate";
		}

	}

	/**
	 * Modifier le mdp d'un client
	 * 
	 * @return si le retour de la m�thode est =! 0, la modif a fonctionn� sinon,
	 *         si ==0 c'est que la modif n'a pas fonctionner
	 */
	public String updateClientMdp() {
		int verif = clientService.updateClientMdp(this.client);

		if (verif != 0) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le mot de passe a �t� modifi�"));
			return "clientAccueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produit"));
			return "clientUpdateMdp";
		}

	}
}