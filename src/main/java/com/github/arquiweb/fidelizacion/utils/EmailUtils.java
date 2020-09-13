package com.github.arquiweb.fidelizacion.utils;

import com.github.arquiweb.fidelizacion.model.Cliente;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
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


        // Carga propiedades para envio de emails
        String resourceName = "../../resources/mail.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            props.load(resourceStream);
        }
        // Iniciar sesión para envío de mail y construir mensaje
        Session session = Session.getInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(props.getProperty("mail.smtp.user")));
            InternetAddress toAddress = new InternetAddress(cliente.getEmail());
            message.addRecipient(Message.RecipientType.TO, toAddress);

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
