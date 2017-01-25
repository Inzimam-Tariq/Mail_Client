/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import org.itsoftsolutions.model.EmailAccountBean;
import org.itsoftsolutions.model.EmailConstants;

/**
 *
 * @author Inzimam
 */
public class SendEmailService extends Service<Integer> {

    private int result;
    private EmailAccountBean emailAccountBean;
    private String subject;
    private String recepient;
    private String content;
    private List<File> attachments = new ArrayList<>();

    public SendEmailService(EmailAccountBean emailAccountBean, String subject, String recepient,
            String content, List<File> attachments) {
        this.emailAccountBean = emailAccountBean;
        this.subject = subject;
        this.recepient = recepient;
        this.content = content;
        this.attachments = attachments;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {

            @Override
            protected Integer call() throws Exception {

                String zohoHost = "smtp.zoho.com";
                String gmailHost = "smtp.gmail.com";
                String portOther = "587";
                String mail = "contact@itsoftsolutions.org";
                String gmail = "inzi.programmer@gmail.com";
                String passGmail = "769inzimam-9771";
                String pass = "Chand-977";

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", gmailHost);
                props.put("mail.smtp.port", portOther);
                // Get the Session object.

                Session session = null;
                try {
                    session = Session.getInstance(props,
                            new javax.mail.Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(gmail, passGmail);
                                }
                            });
                    session.setDebug(true);

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(gmail, "IT Soft"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
//                    if (!ccMail.isEmpty()) {
//                        message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccMail));
//                    }
                    message.setSubject(subject);
                    message.setSentDate(new Date());

                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(content, "text/html");
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                    MimeBodyPart mbp;

                    if (attachments.size() > 0) {
                        for (File attachment : attachments) {
                            messageBodyPart = new MimeBodyPart();
                            DataSource source = new FileDataSource(attachment);
                            messageBodyPart.setDataHandler(new DataHandler(source));
                            messageBodyPart.setFileName(attachment.getName());
                            multipart.addBodyPart(messageBodyPart);
                        }
                    }
                    message.setContent(multipart);

//            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
//            t.connect("smtp.gmail.com", senderMail, pass);
//            t.sendMessage(message, message.getAllRecipients());
//            t.close();
                    Transport.send(message);
                    result = EmailConstants.MESSAGE_SEND_OK;

                } catch (MessagingException ex) {
                    result = EmailConstants.MESSAGE_SEND_ERROR;
                    if (ex instanceof AuthenticationFailedException) {
                        JOptionPane.showMessageDialog(null, "Exception: \n" + "Athentication failed due to invalid sender mail or password\n");
                    } else if (ex instanceof SendFailedException) {
                        JOptionPane.showMessageDialog(null, "within exception");

                        SendFailedException e = (SendFailedException) ex;
                        final Address[] invalidAddresses = e.getInvalidAddresses();

//                setFailedAddresses(invalidAddresses);
                        final Address[] validSentAddresses = e.getValidSentAddresses();
//                setConfirmedAddresses(validSentAddresses);
                        final Address[] validUnsentAddresses = e.getValidUnsentAddresses();
//                resend(validUnsentAddresses);
                        JOptionPane.showMessageDialog(null, "Exception: \n: "
                                + "invalid mail address = " + invalidAddresses.length
                                + "\nvalid addrs = " + validSentAddresses.length
                                + "\nvalid not treated = " + validUnsentAddresses.length);
                    } else if (ex instanceof NoSuchProviderException) {
                        JOptionPane.showMessageDialog(null, "Exception: \n "
                                + "No provider found with the name you specified");
                    } else if (ex instanceof SendFailedException) {
                        JOptionPane.showMessageDialog(null, "Exception: \n " + ex);
                    }
                }
                return result;
            }
        };
    }

}
