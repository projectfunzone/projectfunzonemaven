package fr.adaming.managedbeans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Admin;
import fr.adaming.service.IAdminService;

/**
 * 
 * ManagedBean de l'admin
 * 
 * @author Camille
 *
 */

@SuppressWarnings("serial")
@ManagedBean(name = "adMB")
@RequestScoped
public class AdminManagedBean implements Serializable {

	// declaration des attributs
	private Admin admin;

	/**
	 * Transformation de l'association UML en java, avec instanciation EJB
	 */
	@ManagedProperty(value = "#{adService}")
	private IAdminService adminService;

	/**
	 * 
	 */
	public AdminManagedBean() {
		super();
		this.admin = new Admin();
	}

	/**
	 * @return the admin
	 */
	public Admin getAdmin() {
		return admin;
	}

	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String seConnecter() {
		
		//recuperer l'admin
		Admin adIn = adminService.connectionAdmin(this.admin);

		if (adIn != null) {

			// mettre l'administrateur dans la Session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("adminSession", adIn);

			return "adminAccueil";
		} else {
			
			//message d'erreur
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Connexion fail"));

		}
		return "adminLogin";
	}

}
