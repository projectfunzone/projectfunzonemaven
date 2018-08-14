package fr.adaming.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * permet de définir une commande, Class persistante dans la base de donnée. Auteur :
 * Camille
 */
@SuppressWarnings("serial")
@Entity
@Table(name="commandes")
public class Commande implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_commande")
	private Long idCommande;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCommande;

	
	/**
	 * Transformation de l'association UML en JAVA
	 */
	@ManyToOne
	@JoinColumn(name="client_id", referencedColumnName="id_client")
	private Client cl;
	
	/**
	 * Transformation de l'association UML en JAVA
	 */
	@OneToMany(mappedBy="commande")
	private List<LigneCommande> listeLigneCommande;
	
	/**
	 * Constructeur vide
	 */
	public Commande() {
		super();
	}

	/**
	 * Constructeur avec params sans id
	 * @param dateCommande
	 */
	public Commande(Date dateCommande) {
		super();
		this.dateCommande = dateCommande;
	}

	/**
	 * Constructeur avec params et id
	 * @param idCommande
	 * @param dateCommande
	 */
	public Commande(Long idCommande, Date dateCommande) {
		super();
		this.idCommande = idCommande;
		this.dateCommande = dateCommande;
	}

	/**
	 * @return the idCommande
	 */
	public Long getIdCommande() {
		return idCommande;
	}

	/**
	 * @param idCommande the idCommande to set
	 */
	public void setIdCommande(Long idCommande) {
		this.idCommande = idCommande;
	}

	/**
	 * @return the dateCommande
	 */
	public Date getDateCommande() {
		return dateCommande;
	}

	/**
	 * @param dateCommande the dateCommande to set
	 */
	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}


	/**
	 * @return the cl
	 */
	public Client getCl() {
		return cl;
	}

	/**
	 * @param cl the cl to set
	 */
	public void setCl(Client cl) {
		this.cl = cl;
	}

	/**
	 * @return the listeLigneCommande
	 */
	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	/**
	 * @param listeLigneCommande the listeLigneCommande to set
	 */
	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	
	
}
