package biz.buildit.service;

import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.mail.SimpleMailMessage;
import org.w3c.dom.Document;

import biz.buildit.beans.PropertiesHolder;
import biz.buildit.main.Invoice;
import biz.buildit.main.PurchaseOrder;
import biz.buildit.util.InvoiceStatus;
import biz.buildit.util.MailClient;

public class InvoiceAutomaticProcessor {

	public void process(Document invoiceMail) throws NumberFormatException, XPathExpressionException{
		Invoice invoice = new Invoice();
		XPath xPath = XPathFactory.newInstance().newXPath();
		invoice.setTotal(Float.parseFloat(xPath.evaluate("//total",invoiceMail)));
		invoice.setDueDate(new Date(Long.parseLong(xPath.evaluate("//dueDate",invoiceMail))));
		invoice.setInvoiceStatus(InvoiceStatus.DUEPAYMENT);
		invoice.setPurchaseOrder(PurchaseOrder.findPurchaseOrder(Long.parseLong(xPath.evaluate("//poId",invoiceMail))));
		invoice.persist();
		if(makePayment(invoice))
			invoice.setInvoiceStatus(InvoiceStatus.PAID);
		PropertiesHolder propertiesHolder = PropertiesHolder.getInstance();
		MailSender mailSender = MailSender.getInstance(MailClient.GMAIL);
		SimpleMailMessage simpleMailMessage =
				new SimpleMailMessage();
		simpleMailMessage.setSubject("Payment being processed");
		simpleMailMessage.setTo(propertiesHolder.getRentItEmailAddress());
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simpleMailMessage.setText("Your payment has been made.");
		mailSender.send(simpleMailMessage);
	}
	
	private boolean makePayment(Invoice invoice){
		if(invoice.getInvoiceStatus() == InvoiceStatus.DUEPAYMENT)
			return true;
		else
			return false;
	}
}
