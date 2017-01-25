/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.helpingClasses;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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

/**
 *
 * @author Inzimam
 */
public class SendAttachmentMail {

    private static void setFailedAddresses(Address[] invalidAddresses) {
        if (invalidAddresses != null) {
            String invalid = null;
            for (Address addr : invalidAddresses) {
                invalid += addr.toString() + "\n";
            }
            JOptionPane.showMessageDialog(null, invalid);
        }
    }

    private static void setConfirmedAddresses(Address[] validSentAddresses) {
        if (validSentAddresses != null) {
            String valid = null;
            for (Address addr : validSentAddresses) {
                valid += addr.toString() + "\n";
            }
            JOptionPane.showMessageDialog(null, "Valid Addrs:\n" + valid);
        }
    }
    static String s = "inzi769@outlook.com,inzi.ta@gmail,inzi.programmer@gmail.com,inzi@gmail.com,msgsf15m005@puci.edu.pk";

    private static void resend(Address[] validUnsentAddresses) {

        if (validUnsentAddresses != null) {
            String valid = null;
            for (Address addr : validUnsentAddresses) {
                valid += addr.toString() + "\n";
            }
            JOptionPane.showMessageDialog(null, "Valid Addrs but mail not sent:\n" + valid);
        }

//        sendJavaMail(ComposeMail.user, ComposeMail.passw, Arrays.toString(validUnsentAddresses), null, ComposeMail.server, ComposeMail.msgTitle, ComposeMail.msg, ComposeMail.selectedFile);
    }

    public SendAttachmentMail() {

    }

    static void sendJavaMail(String mail, String pass, String toMail, String ccMail, String host, String msgTitle, String msg, List<File> file) {

        String portZoho = "465";
        String portOther = "587";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", portOther);
        // Get the Session object.
        Session session = null;
        try {
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mail, pass);
                        }
                    });
            session.setDebug(true);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
            if (!ccMail.isEmpty()) {
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccMail));
            }
            message.setSubject(msgTitle);
            message.setSentDate(new Date());

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(msg, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            MimeBodyPart mbp;

            if (file.size() > 0) {
                for (File attachment : file) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(attachment.getName());
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            message.setContent(multipart, "text/html");

//            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
//            t.connect("smtp.gmail.com", senderMail, pass);
//            t.sendMessage(message, message.getAllRecipients());
//            t.close();
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "your mail has been sent successfully!");

        } catch (MessagingException ex) {
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

    }
}
