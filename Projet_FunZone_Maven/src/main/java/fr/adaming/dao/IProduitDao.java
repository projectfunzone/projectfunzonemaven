package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Produit;

public interface IProduitDao {
	
	public List<Produit> getAllProduit ();
	
	public List<Produit> searchProduitBy (Produit produit);
	
	public Produit searchProduitById (Produit produit);

}
