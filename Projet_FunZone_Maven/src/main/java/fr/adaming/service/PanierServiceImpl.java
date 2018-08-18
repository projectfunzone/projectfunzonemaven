package fr.adaming.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Transactional
@Service("panService")
public class PanierServiceImpl implements IPanierService {

	@Autowired //injection d�pendance d'un collaborateur
	ICommandeService commandeService;

	//Setter pour l'injection d�pendance
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}
	
	@Autowired //injection d�pendance d'un collaborateur
	IClientService clientService;

	//Setter pour l'injection d�pendance
	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}
	
	
	@Autowired //injection d�pendance d'un collaborateur
	ILigneCommandeService lignecommandeService;

	//Setter pour l'injection d�pendance
	public void setLignecommandeService(ILigneCommandeService lignecommandeService) {
		this.lignecommandeService = lignecommandeService;
	}
	
	@Autowired //injection d�pendance d'un collaborateur
	IProduitService produitService;
	
	//Setter pour l'injection d�pendance
	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}




	/**
	 *  Le produit choisi par le client ainsi que sa quantit� sont
	 * en parametre. On cr�e une ligne de commande et on v�rifi� que la quantit�
	 * demand�e est disponible 
	 * Si c�est le cas alors on ajoute � la ligne de commande. 
	 * On stocke le produit, la quantit� et le prix dans la ligne de
	 * commande 
	 * @return En sortie on r�cup�re lc.
	 */
	@Override
	public LigneCommande ajoutProdPanier(Produit pr, int quantite) {

		LigneCommande lc = new LigneCommande();
		

		if (quantite <= pr.getQuantite()) {
			
			lc.setProduit(pr);
			lc.setQuantite(quantite);
			lc.setPrix(pr.getPrix() * quantite);
			
			return lc;
		} else {
			
			return null;
		}

	}
		
		


}
