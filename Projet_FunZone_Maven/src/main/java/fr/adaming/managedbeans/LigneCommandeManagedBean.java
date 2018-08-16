package fr.adaming.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.LigneCommande;
import fr.adaming.service.ILigneCommandeService;

@SuppressWarnings("serial")
@ManagedBean(name = "lcMB")
@RequestScoped
public class LigneCommandeManagedBean implements Serializable {

	/**
	 * Declaration des attributs du ManagedBean LigneCommande
	 */
	HttpSession maSession;
	private LigneCommande ligneCommande;
	private boolean indice;
	private List<LigneCommande> listeLC;

	/**
	 * Transformation de l'association UML en java
	 */
	@ManagedProperty(value = "lcService")
	private ILigneCommandeService ligneCommandeService;

	/**
	 * Constructeur vide du ManagedBean
	 */
	public LigneCommandeManagedBean() {
		super();
		this.ligneCommande = new LigneCommande();
		this.indice = false;
	}

	@PostConstruct
	public void init() {
		this.listeLC = ligneCommandeService.getAllLigneCommandes();
	}

	/**
	 * Declaration des getteurs et setteurs
	 */

	/**
	 * @return LigneCommande
	 */
	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	/**
	 * @param LigneCommande
	 */
	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	/**
	 * @return indice
	 */
	public boolean isIndice() {
		return indice;
	}

	/**
	 * @param indice
	 * 
	 */
	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	public ILigneCommandeService getLcService() {
		return ligneCommandeService;
	}

	public void setLcService(ILigneCommandeService lcService) {
		this.ligneCommandeService = lcService;
	}

	/**
	 * @return listeLigneCommande
	 */
	public List<LigneCommande> getListeLC() {
		return listeLC;
	}

	/**
	 * @param listeLigneCommande
	 */
	public void setListeLC(List<LigneCommande> listeLC) {
		this.listeLC = listeLC;
	}
	

	/**
	 * Modifier une ligne du site
	 */
	public String updateLigneCommande() {

		if (ligneCommandeService.updateLigneCommande(this.ligneCommande) != 0) {

			// envoie vers la page XHTML accueil de l'administrateur
			return "";

		} else {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modification invalide"));

			// renvoie vers la page XHTML d'ajout d'une ligne de commande
			return "";
		}
	}

	/**
	 * supprimer une ligne du site
	 */
	public String deleteLigneCommande() {

		if (ligneCommandeService.deleteLigneCommande(this.ligneCommande) != 0) {

			// envoie vers la page XHTML accueil de l'administrateur
			return "";

		} else {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression invalide"));
			// renvoie vers la page XHTML d'ajout d'une ligne de commande
			return "";
		}
	}

	/**
	 * rechercher une ligne du site par son id
	 */
	public String searchLigneCommandebyId() {

		// recherche et stockage de la ligne de commande recherchée
		LigneCommande lcSearch = ligneCommandeService.getLigneCommandebyId(ligneCommande);

		// On test le bon résultat de la recherche
		if (lcSearch != null) {

			// on stocke la recherche dans l'attribut du ManagedBean
			this.indice = true;
			this.ligneCommande = lcSearch;

		} else {

			this.indice = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Supression invalide"));

		}
		// envoie vers la page XHTML recherche mise à jour avec le resultat de
		// la recherche
		return "";
	}

}
