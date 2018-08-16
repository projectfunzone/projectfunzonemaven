package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieService {

	public List<Categorie> getAllCategorie();
	
	public Categorie getCategorieByIdOrNom (Categorie categorie);
	
	public Categorie addCategorie (Categorie categorie);
}
