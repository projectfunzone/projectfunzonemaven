package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;

/**
 * Interface de la couche ClientService qui d�finit les m�thodes m�tiers de Client
 * 
 * @author Adaming
 *
 */

public interface IClientService {

	/**
	 * G�n�re la liste de tous les clients enregistr�s
	 * @return une list de Client
	 */
	public List<Client> getAllClients();

	/**
	 * Recherche un client par son id. 
	 * @return un Client
	 */
	public Client getClientById(Client cl);

	/**
	 * Recherche un client par son nom
	 * @return une liste de Client
	 */
	public List<Client> getClientByIdNomMail(Client cl);

	/**
	 * Ajouter un client
	 * @return 
	 */
	public Client addClient(Client cl);
	
	/**
	 * Supprimer un client
	 * @return 
	 */
	public int deleteClient(Client cl);

	/**
	 * Modifier un compte client dans la base de donn�e
	 * @return retourne 1 si modifi�, retourne 0 si non modifi�
	 */
	public int updateClient(Client cl);
	
	
	/**
	 * Modifier le mdp d'un client  dans la base de donn�e
	 * @return retourne 1 si modifi�, retourne 0 si non modifi�
	 */
	public int updateClientMdp(Client cl);
}
