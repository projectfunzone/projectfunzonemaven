package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * permet de définir un admin, Class persistante dans la base de donnée Auteur :
 * Camille
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "admins")
public class Admin implements Serializable {

	//Attribut
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_admin")
	private Long idAdmin;
	private String mdpAdmin;

	
	//Constructeur
	/**
	 * Constructeur vide
	 */
	public Admin() {
		super();
	}

	/**
	 * Constructeur avec params admin sans id
	 * 
	 * @param mdpAdmin
	 */
	public Admin(String mdpAdmin) {
		super();
		this.mdpAdmin = mdpAdmin;
	}

	/**
	 * Constructeur avec params avec id
	 * 
	 * @param idAdmin
	 * @param mdpAdmin
	 */
	public Admin(Long idAdmin, String mdpAdmin) {
		super();
		this.idAdmin = idAdmin;
		this.mdpAdmin = mdpAdmin;
	}

	
	//getter et setter
	/**
	 * @return the idAdmin
	 */
	public Long getIdAdmin() {
		return idAdmin;
	}

	/**
	 * @param idAdmin
	 *            the idAdmin to set
	 */
	public void setIdAdmin(Long idAdmin) {
		this.idAdmin = idAdmin;
	}

	/**
	 * @return the mdpAdmin
	 */
	public String getMdpAdmin() {
		return mdpAdmin;
	}

	/**
	 * @param mdpAdmin
	 *            the mdpAdmin to set
	 */
	public void setMdpAdmin(String mdpAdmin) {
		this.mdpAdmin = mdpAdmin;
	}

	// ToString
	@Override
	public String toString() {
		return "Admin [idAdmin=" + idAdmin + ", mdpAdmin=" + mdpAdmin + "]";
	}

}
