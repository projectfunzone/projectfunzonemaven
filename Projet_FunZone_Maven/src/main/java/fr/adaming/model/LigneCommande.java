package fr.adaming.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Definition de la classe LigneCommande
 */
@Entity
@Table(name = "ligneCommandes")
public class LigneCommande {

	// Declaration des attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_ligneCommande")
	private Long idLigneCommande;
	private int quantite;
	private double prix;

	/**
	 * Transformation de l'association UML en JAVA entre Ligne de Commande et
	 * Produit
	 */
	@ManyToOne
	@JoinColumn(name = "produit_id", referencedColumnName = "id_produit")
	private Produit produit;

	/**
	 * Transformation de l'association UML en JAVA entre Ligne de Commande et
	 * Commande
	 */
	@ManyToOne
	@JoinColumn(name = "commande_id", referencedColumnName = "id_commande")
	private Commande commande;

	/**
	 * Declaration des constructeurs
	 */
	/**
	 * Constructeur vide
	 */
	public LigneCommande() {
		super();
	}

	/**
	 * Constructeur complet sans id
	 * 
	 * @param quantite
	 * @param prix
	 */
	public LigneCommande(int quantite, int prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}

	/**
	 * Constructeur complet avec id
	 * 
	 * @param idLigneCommande
	 * @param quantite
	 * @param prix
	 */
	public LigneCommande(Long idLigneCommande, int quantite, int prix) {
		super();
		this.idLigneCommande = idLigneCommande;
		this.quantite = quantite;
		this.prix = prix;
	}

	/*
	 * Declaration des getteurs et setteurs
	 */

	/**
	 * @return the idLigneCommande
	 */
	public Long getIdLigneCommande() {
		return idLigneCommande;
	}

	/**
	 * @param idLigneCommande
	 *            the idLigneCommande to set
	 */
	public void setIdLigneCommande(Long idLigneCommande) {
		this.idLigneCommande = idLigneCommande;
	}

	/**
	 * @return the quantite
	 */
	public int getQuantite() {
		return quantite;
	}

	/**
	 * @param quantite
	 *            the quantite to set
	 */
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	/**
	 * @return the prix
	 */
	public double getPrix() {
		return prix;
	}

	/**
	 * @param d
	 *            the prix to set
	 */
	public void setPrix(double d) {
		this.prix = d;
	}

	/**
	 * @return the produit
	 */
	public Produit getProduit() {
		return produit;
	}

	/**
	 * @param produit
	 *            the produit to set
	 */
	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	/**
	 * @return the commande
	 */
	public Commande getCommande() {
		return commande;
	}

	/**
	 * @param commande
	 *            the commande to set
	 */
	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	// methode
	@Override
	public String toString() {
		return "LigneCommande [quantite=" + quantite + ", prix=" + prix + "]";
	}

}
