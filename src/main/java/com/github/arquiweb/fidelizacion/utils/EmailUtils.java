package com.github.arquiweb.fidelizacion.utils;

import com.github.arquiweb.fidelizacion.model.Cliente;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class EmailUtils {

    public void sendEmailConfirmacionCanje(Cliente cliente, Integer puntosCanjeados,
                                           String concepto, Date fechaCanje) throws IOException {

        System.out.println("Construyendo mail de confirmación de canje");
        // Add recipient
        String to = cliente.getEmail();
        // Add sender
        String from = "arquiwebclass@gmail.com";

        String host = "smtp.gmail.com";

        final Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", "arquiwebclass@gmail.com");
        props.put("mail.smtp.password", "arquiweb");
        props.put("mail.smtp.port", "587");

        // Get the Session object
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
                    }
                });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject
            message.setSubject("Confirmación de canje de puntos");
            message.setText("Estimado Sr./Sra. " + cliente.getApellido() +"\n" +
                            "Se realizó el canje de " +puntosCanjeados + " puntos en concepto de " +
                            concepto + " en la fecha " + fechaCanje.toString()+ ".");

            Transport transport = session.getTransport("smtp");
            transport.connect(props.getProperty("mail.smtp.host"),
                                props.getProperty("mail.smtp.user"),
                                props.getProperty("mail.smtp.password"));
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail de confirmación de canje enviado");
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
