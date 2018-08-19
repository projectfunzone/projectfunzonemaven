package fr.adaming.managedbeans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Client;

@ManagedBean(name = "headerMB")
@RequestScoped
public class HeaderManagedBean {

	private String header;



	public HeaderManagedBean() {
		super();

	}

	@PostConstruct
	public void init() {
		// Récupérer le client dans la session
		Client clOut = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("clSession");

		if (clOut != null) {
			this.header="templates/templateClientConnectHeader.xhtml";
		} else {
			this.header="templates/templateClientHeader.xhtml";
		}

	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
