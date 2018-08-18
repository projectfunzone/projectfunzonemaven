package fr.adaming.service;

import java.io.OutputStream;

import fr.adaming.model.Client;

public interface IEmailService {

	
	public void email(Client cl, String objetMail, String corpsMail, String nomPDF, String corpsPDF);
	
	public void writePdf(OutputStream outputStream, String corpsPDF) throws Exception ;
}
