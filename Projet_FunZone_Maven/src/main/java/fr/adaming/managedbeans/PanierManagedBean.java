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
import javax.management.ListenerNotFoundException;

import fr.adaming.model.Client;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.model.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.IPanierService;
import fr.adaming.service.IProduitService;

@SuppressWarnings("serial")
@ManagedBean(name = "panMB")
@RequestScoped
public class PanierManagedBean implements Serializable {

	@ManagedProperty(value = "#{panService}") // injection dépendance
	private IPanierService panierService;

	// Setter pour l'injection dépendance
	public void setPanierService(IPanierService panierService) {
		this.panierService = panierService;
	}

	@ManagedProperty(value = "#{prService}") // injection dépendance
	private IProduitService produitService;

	// Setter pour l'injection dépendance
	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	@ManagedProperty(value = "#{clService}") // injection dépendance
	private IClientService clientService;

	// Setter pour l'injection dépendance
	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	@ManagedProperty(value = "#{cmdService}") // injection dépendance
	private ICommandeService commandeService;

	// Setter pour l'injection dépendance
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	/**
	 * Les attributs
	 */
	private Panier panier;
	private Produit produit;
	private int quantite;
	private LigneCommande ligneCommande;
	private List<LigneCommande> listePanier = new ArrayList<>();
	private double prixTotal;

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

		// récupère le panier dans la session
		Panier panSession = (Panier) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("panierClient");

		// on récupere la session du panier et on verifie qu'elle ne soit pas
		// vide
		if (panSession != null) {

			// on recupere la liste de commande du panier et on verifie qu'elle
			// ne soit pas vide
			if (panSession.getListeCommande() != null) {

				// on stocke la nouvelle liste dans la nouvelle
				for (LigneCommande lc : panSession.getListeCommande()) {
					if (lc.getQuantite() != 0) {
						this.listePanier.add(lc);

					}

					System.out.println(this.prixTotal);
					// calcul du prix total de la commande
					this.prixTotal = panierService.calculTotalPanier(panSession.getListeCommande());

					System.out.println(this.prixTotal);
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

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
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

	public double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}

	/**
	 * Ajouter un produit dans son panier en passant par une ligne de commande.
	 * Ce panier n'est pas stocké dans la base de donnée (transient) mais
	 * uniquement dans la session créée pour l'occasion
	 */
	public String addProdPanier() {

		// le panier dans la session est récupérer dans le postConstruct

		// on récupere le produit de la base de donnée.
		Produit prOut = produitService.searchProduitById(this.produit);

		// on vérifie que le produit existe
		if (prOut != null) {

			// création d'un indice en int dont la valeur sera modifié à 1 si le
			// produit était déjà présent dans le panier, sinon, il reste à zéro
			// pour faire la création d'une ligne de commande
			int verifAjoutPanier = 0;

			// on verifie que le panier a déjà des entrées, sinon, on ajoute
			// directement une ligne de commande dedans
			if (this.listePanier.size() > 0) {

				for (LigneCommande lc : listePanier) {
					// Pour chaque ligne de commande, on vérifie si le produit
					// n'est pas déjà présent dans le panier, en vérifiant si
					// l'id du produit correspond à l'id du produit dans la
					// ligne de commande
					if (lc.getProduit().getIdProduit() == prOut.getIdProduit()) {

						// si l'id est identique, on vérifie que la quantité
						// déjà
						// commandé + la quantité rajouté sont inférieur au
						// stock
						// disponible de produit

						if ((this.quantite + lc.getQuantite()) <= prOut.getQuantite()) {

							lc.setQuantite(lc.getQuantite() + this.quantite);
							lc.setPrix(lc.getPrix() + (this.quantite * prOut.getPrix()));

							// on passe l'indice à 1 vu que le produit a été
							// ajouté dans une ligne déjà existante
							verifAjoutPanier = 1;

							// on ajoute au panier la liste de commande
							panier.setListeCommande(this.listePanier);

							// pour calculer le prix total de la commande
							this.prixTotal = panierService.calculTotalPanier(panier.getListeCommande());

							return "panierAfficher";

						} else {

							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage("La quantité en stock n'est pas suffisante"));
							return "";
						}

					}

				}

			}

			// si l'indice verifAjoutPanier est tjs à 0, c'est que le produit
			// n'existait pas dans le panié et qu'il n'a pas été ajouté
			if (verifAjoutPanier == 0) {

				// Création d'une ligne de commande
				LigneCommande lcOut = panierService.addProdPanier(prOut, this.quantite);

				// vérification que la ligne de commande a été créée
				if (lcOut != null) {

					// on ajoute à la liste de ligne de commande cette nouvelle
					// nouvelle ligne de commande
					this.listePanier.add(lcOut);

					// on ajoute au panier la liste de commande
					panier.setListeCommande(this.listePanier);

					// pour calculer le prix total de la commande
					this.prixTotal = panierService.calculTotalPanier(panier.getListeCommande());

					// on ajoute à la session PanierClient la nouveau panier
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("panierClient", panier);

					return "panierAfficher";

				} else {

					// Message d'erreur suite à la tentative d'ajout de produit
					// au
					// panier
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Le produit n'est pas en quantité suffisante"));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur s'est produite"));
			}

		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Le produit n'existe pas dans la base de donnée"));
		}
		// on renvoie au panier
		return "";

	}

	public String ajoutProdPanierDirect() {

		panierService.addProdPanierDirect(this.ligneCommande, this.quantite);

		// on ajoute au panier la liste de commande
		panier.setListeCommande(this.listePanier);

		// pour calculer le prix total de la commande
		this.prixTotal = panierService.calculTotalPanier(panier.getListeCommande());

		// on ajoute à la session PanierClient la nouveau panier
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("panierClient", panier);

		return "";
	}

	public String supprProdPanierDirect() {

		if (this.ligneCommande.getQuantite() > 0) {
			panierService.supprProdPanierDirect(this.ligneCommande, this.quantite);

			// permet de supprimer la ligne si la quantité est à 0
			if (this.ligneCommande.getQuantite() == 0) {
				this.listePanier.remove(this.ligneCommande);

			}

			// on ajoute au panier la liste de commande
			panier.setListeCommande(this.listePanier);

			// pour calculer le prix total de la commande
			this.prixTotal = panierService.calculTotalPanier(panier.getListeCommande());

			// on ajoute à la session PanierClient la nouveau panier
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("panierClient", panier);
		}

		return "";
	}

}
