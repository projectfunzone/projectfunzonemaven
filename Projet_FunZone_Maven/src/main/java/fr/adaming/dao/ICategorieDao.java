package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieDao {
	
	public List<Categorie> getAllCategorie () ;
	
	public Categorie getCategorieByIdOrNom (Categorie categorie);

}
