package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * permet de définir un client, Class persistante dans la
 * base de donnée
 * Auteur : Camille
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "clients")
public class Client implements Serializable {

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_client")
	private Long idClient;
	private String nomClient;
	private String adresse;
	
	@Column(unique=true)
	private String email;
	private String tel;
	private String mdpClient;
	
	
	/**
	 * Transformation de l'association UML en JAVA
	 */
	@OneToMany(mappedBy="cl")
	private List<Commande> listeCommande;

	// constructeur
	/**
	 * Constructeur vide
	 */
	public Client() {
		super();
	}

	/**
	 * Constructeur avec tous les paramètres du client sans id
	 * 
	 * @param nomClient
	 * @param adresse
	 * @param email
	 * @param tel
	 * @param mdpClient
	 */
	public Client(String nomClient, String adresse, String email, String tel, String mdpClient) {
		super();
		this.nomClient = nomClient;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
		this.mdpClient = mdpClient;
	}

	/**
	 * Constructeur avec tous les paramètres + id
	 * 
	 * @param idClient
	 * @param nomClient
	 * @param adresse
	 * @param email
	 * @param tel
	 * @param mdpClient
	 */
	public Client(Long idClient, String nomClient, String adresse, String email, String tel, String mdpClient) {
		super();
		this.idClient = idClient;
		this.nomClient = nomClient;
		this.adresse = adresse;
		this.email = email;
		this.tel = tel;
		this.mdpClient = mdpClient;
	}

	// getter et setter

	/**
	 * @return the idClient
	 */
	public Long getIdClient() {
		return idClient;
	}

	/**
	 * @param idClient
	 *            the idClient to set
	 */
	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	/**
	 * @return the nomClient
	 */
	public String getNomClient() {
		return nomClient;
	}

	/**
	 * @param nomClient
	 *            the nomClient to set
	 */
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse
	 *            the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the mdpClient
	 */
	public String getMdpClient() {
		return mdpClient;
	}

	/**
	 * @param mdpClient
	 *            the mdpClient to set
	 */
	public void setMdpClient(String mdpClient) {
		this.mdpClient = mdpClient;
	}

	// ToString
	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", nomClient=" + nomClient + ", adresse=" + adresse + ", email=" + email
				+ ", tel=" + tel + ", mdpClient=" + mdpClient + "]";
	}
}
