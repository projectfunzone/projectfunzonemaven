package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Client;

/**
 * Interface de la couche ClientDao qui d�finit les m�thodes d'un Client
 * 
 * @author Adaming
 *
 */
public interface IClientDao {

	/**
	 * Recuperation de la liste de tous les clients
	 */
	public List<Client> getAllClients();

	/**
	 * Recherche un client par son id
	 */
	public Client getClientById(Client cl);

	/**
	 * Rechercher un client par son nom
	 */
	public List<Client> getClientByIdNomMail(Client cl);

	/**
	 * Cr�er un client dans la base de donn�e
	 */
	public Client addClient(Client cl);

	/**
	 * Supprimer un client dans la base de donn�e 
	 * @return retourne 1 si supprim�, retourne 0 si non supprim�
	 */
	public int deleteClient(Client cl);

	/**
	 * Modifier un compte client (toutes les infos sauf le mdp) dans
	 * la base de donn�e 
	 * @return retourne 1 si modifi�, retourne 0 si non modifi�
	 */
	public int updateClient(Client cl);

	/**
	 * Modifier le mdp d'un client dans la base de donn�e 
	 * @return retourne 1 si modifi�, retourne 0 si non modifi�
	 */
	public int updateClientMdp(Client cl);

}
