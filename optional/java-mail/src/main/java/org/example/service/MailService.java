package org.example.service;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Properties;

@Service
public class MailService {
    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private String smtpPort;

    @Value("${mail.smtp.auth}")
    private String smtpAuth;

    @Value("${mail.smtp.ssl.enable}")
    private String sslEnable;

    @Value("${mail.smtp.socketFactory.port}")
    private String socketFactoryPort;

    @Value("${mail.smtp.socketFactory.class}")
    private String socketFactoryClass;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    public void sendMail(String to, String subject, String body, String pathToFile) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.ssl.enable", sslEnable);
        props.put("mail.smtp.socketFactory.port", socketFactoryPort);
        props.put("mail.smtp.socketFactory.class", socketFactoryClass);

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            multipart.addBodyPart(textPart);

            if (pathToFile != null && !pathToFile.isEmpty()) {
                MimeBodyPart filePart = new MimeBodyPart();
                DataSource source = new FileDataSource(new File(pathToFile));
                filePart.setDataHandler(new DataHandler(source));
                filePart.setFileName("attachment");
                multipart.addBodyPart(filePart);
            }

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
