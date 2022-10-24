package cl.tamilla.servicice;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cl.tamilla.utilidades.Constantes;

@Service
@Primary
public class EmailService {
    
    public void sendMail(String mail, String asunto, String mensaje) throws AddressException, MessagingException{ //el mail al que enviamos el correo,, y el asunto
        
        Properties props = new Properties();
        
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", Constantes.SMTP);
		props.put("mail.smtp.port", Constantes.SMTP_PORT);
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() 
			{
		         return new PasswordAuthentication(Constantes.SMTP_MAIL, Constantes.SMTP_PASSWORD);
		    }
		});

        Message msg = new MimeMessage(session);
		
		msg.setFrom(new InternetAddress(Constantes.SMTP_MAIL, false));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject(asunto);
		msg.setContent(mensaje, "text/html");
		msg.setSentDate(new Date(0));
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mensaje, "text/html");
		
		Transport.send(msg);
    }

}
