package fr.adaming.managedbeans;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@SuppressWarnings("serial")
@ManagedBean(name = "catMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	// Attribut
	private Categorie categorie;

	private List<Categorie> listeCategorie;
	private List<Categorie> listeCategorieFiltre;

	private UploadedFile file;

	// injection dépendance dans le conteneur web
	@ManagedProperty(value = "#{catService}")
	private ICategorieService categorieService;

	// Setter obligatoire pour l'injection dépendance
	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	// constructeur vide
	public CategorieManagedBean() {
		super();
		this.categorie = new Categorie();
	}

	// Post construct pour charger la liste des catégories
	@PostConstruct
	public void init() {
		this.listeCategorie = categorieService.getAllCategorie();
	}

	// getter et setter
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<Categorie> getListeCategorie() {
		return listeCategorie;
	}

	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategorie = listeCategorie;
	}

	public List<Categorie> getListeCategorieFiltre() {
		return listeCategorieFiltre;
	}

	public void setListeCategorieFiltre(List<Categorie> listeCategorieFiltre) {
		this.listeCategorieFiltre = listeCategorieFiltre;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * Methode permettant de filtrer en fonction de la recherche par mot clef
	 * 
	 * @return boolean
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
	}

	
	
	//Méthode pour rechercher une catégorie par nom ou id
	public String searchCategorieBy () {
		Categorie catOut=categorieService.getCategorieByIdOrNom(this.categorie);
		
		if (catOut != null) {
			this.categorie=catOut;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Il n'y a pas de catégorie correspondante"));
		}
		return "";
	}
}
