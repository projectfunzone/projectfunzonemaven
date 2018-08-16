package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

public interface IProduitService {
	
	public List<Produit> getAllProduit ();
	
	public List<Produit> searchProduitBy (Produit produit);
	
	public Produit searchProduitById (Produit produit);
	
	public Produit addProduit(Produit pr, Categorie c);

	public int updateProduit(Produit pr, Categorie c);

	public int deleteProduit(Produit pr);

}
