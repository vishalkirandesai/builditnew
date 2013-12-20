package biz.buildit.service;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import biz.buildit.beans.PropertiesHolder;
import biz.buildit.util.MailClient;

public class MailSender extends JavaMailSenderImpl{

	private static MailSender instance;
	private Properties prop;
	PropertiesHolder propertiesHolder;

	public MailSender(){
		instance = new MailSender();
		propertiesHolder = PropertiesHolder.getInstance();
	}

	public MailSender(MailClient client){
		switch(client){
		case GMAIL:
			instance.setUsername(propertiesHolder.getEmailUserName());
			instance.setPassword(propertiesHolder.getEmailPassword());
			instance.setHost("smtp.gmail.com");
			instance.setPort(587);
			prop = new Properties();
			prop.setProperty("mail.smtp.starttls.enable", Boolean.toString(true));
			prop.setProperty("mail.smtp.auth", Boolean.toString(true));
			instance.setJavaMailProperties(prop);
			break;
		default: 
			instance = new MailSender();
			instance.setUsername(propertiesHolder.getEmailUserName());
			instance.setPassword(propertiesHolder.getEmailPassword());
			instance.setHost("smtp.gmail.com");
			instance.setPort(587);
			prop = new Properties();
			prop.setProperty("mail.smtp.starttls.enable", Boolean.toString(true));
			prop.setProperty("mail.smtp.auth", Boolean.toString(true));
			instance.setJavaMailProperties(prop);
			break;
		}
	}
	public static MailSender getInstance(){
		return new MailSender(MailClient.GMAIL);
	}
	
	public static MailSender getInstance(MailClient client){
		return new MailSender(client);
	}
	
	public void setProperty(String key,String value){
		prop.setProperty(key, value);
	}

}
