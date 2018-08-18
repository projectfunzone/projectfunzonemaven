package fr.adaming.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
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

	@Autowired
	ICommandeService commandeService;

	@Autowired
	IClientService clientService;

	@Autowired
	ILigneCommandeService lignecommandeService;

	
	/**
	 * @return the commandeService
	 */
	public ICommandeService getCommandeService() {
		return commandeService;
	}

	/**
	 * @param commandeService the commandeService to set
	 */
	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	/**
	 * @return the clientService
	 */
	public IClientService getClientService() {
		return clientService;
	}

	/**
	 * @param clientService the clientService to set
	 */
	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	/**
	 * @return the lignecommandeService
	 */
	public ILigneCommandeService getLignecommandeService() {
		return lignecommandeService;
	}

	/**
	 * @param lignecommandeService the lignecommandeService to set
	 */
	public void setLignecommandeService(ILigneCommandeService lignecommandeService) {
		this.lignecommandeService = lignecommandeService;
	}

	
	
	/**
	 *  Le produit choisi par le client ainsi que sa quantité sont
	 * en parametre. On crée une ligne de commande et on vérifié que la quantité
	 * demandée est disponible 
	 * Si c’est le cas alors on ajoute à la ligne de commande. 
	 * On stocke le produit, la quantité et le prix dans la ligne de
	 * commande 
	 * @return En sortie on récupère lc.
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
