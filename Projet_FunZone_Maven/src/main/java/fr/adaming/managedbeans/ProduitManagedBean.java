package fr.adaming.managedbeans;

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
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "prMB")
@RequestScoped
public class ProduitManagedBean {

	private Produit produit;

	private Categorie categorie;

	private List<Produit> listeProduit;

	private List<Produit> listeProduitBy;

	private List<Produit> listeProduitFiltre;

	private UploadedFile file;

	@ManagedProperty(value = "#{prService}")
	private IProduitService produitService;

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	// injection dépendance dans le conteneur web
	@ManagedProperty(value = "#{catService}")
	private ICategorieService categorieService;

	// Setter obligatoire pour l'injection dépendance
	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public ProduitManagedBean() {
		super();
		this.produit = new Produit();
		this.categorie = new Categorie();
	}

	@PostConstruct
	public void init() {
		this.listeProduit = produitService.getAllProduit();
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public List<Produit> getListeProduitFiltre() {
		return listeProduitFiltre;
	}

	public void setListeProduitFiltre(List<Produit> listeProduitFiltre) {
		this.listeProduitFiltre = listeProduitFiltre;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<Produit> getListeProduitBy() {
		return listeProduitBy;
	}

	public void setListeProduitBy(List<Produit> listeProduitBy) {
		this.listeProduitBy = listeProduitBy;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

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

	public String searchProduitBy() {
		this.produit.setCategorie(this.categorie);

		this.listeProduitBy = produitService.searchProduitBy(this.produit);

		if (listeProduitBy.size() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Il n'y a pas de produit correspondant"));
		}

		return "";

	}

	public String searchProduitById() {
		
		Produit prOut=produitService.searchProduitById(this.produit);
		
		if (prOut != null) {
			this.produit=prOut;
			Categorie catOut=categorieService.getCategorieByIdOrNom(this.produit.getCategorie());
			
			if (catOut != null){
				this.categorie=catOut;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Une erreur s'est produite"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Il n'y a pas de produit correspondant"));
		}

		return "";

	}
}
