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
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.model.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.IPanierService;
import fr.adaming.service.IProduitService;

@SuppressWarnings("serial")
@ManagedBean(name = "panMB")
@RequestScoped
public class PanierManagedBean implements Serializable {

	@ManagedProperty(value = "#{panService}")
	private IPanierService panierService;

	@ManagedProperty(value = "#{prService}")
	private IProduitService produitService;

	@ManagedProperty(value = "#{clService}")
	private IClientService clientService;

	/**
	 * Les attributs
	 */
	private Panier panier;
	private Produit produit;
	private int quantite;
	private LigneCommande ligneCommande;

	private List<LigneCommande> listePanier = new ArrayList<>();

	/**
	 * Transformation de l'association UML en JAVA
	 */

	/**
	 * Constructeur vide
	 */
	public PanierManagedBean() {
		super();
		this.panier = new Panier();
		this.produit = new Produit();
		this.ligneCommande = new LigneCommande();
	}

	@PostConstruct
	public void init() {

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

	/**
	 * @return the pan
	 */
	public Panier getPanier() {
		return panier;
	}

	/**
	 * @param pan
	 *            the pan to set
	 */
	public void setPanier(Panier pan) {
		this.panier = pan;
	}

	/**
	 * @return the pr
	 */
	public Produit getProduit() {
		return produit;
	}

	/**
	 * @param pr
	 *            the pr to set
	 */
	public void setProduit(Produit pr) {
		this.produit = pr;
	}

	/**
	 * @return the q
	 */
	public int getQ() {
		return quantite;
	}

	/**
	 * @param q
	 *            the q to set
	 */
	public void setQuantite(int q) {
		this.quantite = q;
	}

	/**
	 * @return the lc
	 */
	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	/**
	 * @param lc
	 *            the lc to set
	 */
	public void setLigneCommande(LigneCommande lc) {
		this.ligneCommande = lc;
	}

	/**
	 * @return the listePanier
	 */
	public List<LigneCommande> getListePanier() {
		return listePanier;
	}

	/**
	 * @param listePanier
	 *            the listePanier to set
	 */
	public void setListePanier(List<LigneCommande> listePanier) {
		this.listePanier = listePanier;
	}

	/**
	 * Ajouter un produit dans son panier en passant par
	 * une ligne de commande. Ce panier n'est pas stock� dans la base de donn�e
	 * (transient) mais uniquement dans la session cr��e pour l'occasion
	 */
	public String addProdPanier() {

		// le panier dans la session est r�cup�rer dans le postConstruct

		// on r�cupere le produit de la base de donn�e.
		Produit prOut = panierService.getProduitbyId(this.produit);

		// on cr�er alors la ligne de commande associ�e
		LigneCommande lcOut = panierService.ajoutProdPanier(prOut, quantite);

		// on v�rifie que lc ne soit pas vide
		if (lcOut != null) {

			System.out.println(lcOut);

			System.out.println(this.listePanier);
			// on ajoute � la liste de ligne de commande cette nouvelle nouvelle
			// ligne de commande
			this.listePanier.add(lcOut);

			// on ajoute au panier la liste de commande
			panier.setListeCommande(this.listePanier);

			// on ajoute � la session PanierClient la nouveau panier
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("panierClient", panier);
			// on renvoie au panier
			return "panierClient";

		} else {

			// Message d'erreur suite � la tentative d'ajout de produit au
			// panier
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("l'ajout a �chou�"));

			return "addPanier";
		}

	}

	/**
	 * M�thode qui permet de cr�er la commande avec les produits et les
	 * quantit�s associ�
	 * 
	 * @return
	 */
	public String cr�erCommande() {

		// R�cup�rer le client dans la session
		Client clOut = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clSession");

		if (clOut != null) {

			// cr�er une commande avec le panier qui est dans la session
			int verif = panierService.cr�erCommande(this.listePanier, clOut);

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
