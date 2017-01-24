/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import org.itsoftsolutions.model.EmailMessageBean;

/**
 *
 * @author Inzimam
 */
public class MessageRendererService extends Service<Void> {

    private EmailMessageBean msgToRender;
    private WebEngine msgRendererEngine;
    private StringBuffer sb = new StringBuffer();

    public MessageRendererService(WebEngine msgRendererEngine) {
        this.msgRendererEngine = msgRendererEngine;
        this.setOnSucceeded(e -> {
            showMessage();
        });
    }

    public void setMsgToRender(EmailMessageBean msgToRender) {
        this.msgToRender = msgToRender;
    }

    private void RenderMessage() {
        sb.setLength(0);
        msgToRender.clearAttachments();
        Message msg = msgToRender.getMsgRef();
        try {
//            String messageType = msg.getContentType();

            sb.append(getText(msg));

            if (hasAttachments(msg)) {
                Multipart mp = (Multipart) msg.getContent();
                for (int i = mp.getCount() - 1; i >= 0; i--) {
                    BodyPart bp = mp.getBodyPart(i);

                    MimeBodyPart mbp = (MimeBodyPart) bp;
                    msgToRender.addAttachment(mbp);
                }
            }
//            if (messageType.contains("TEXT/HTML")
//                    || messageType.contains("TEXT/PLAIN")
//                    || messageType.contains("text")) {
//                sb.append(msg.getContent().toString());
//            } else if (messageType.contains("multipart")) {
//                Multipart mp = (Multipart) msg.getContent();
//                for (int i = mp.getCount() - 1; i >= 0; i--) {
//                    BodyPart bp = mp.getBodyPart(i);
//                    String contentType = bp.getContentType();
//                    if (contentType.contains("TEXT/HTML")
//                            || contentType.contains("TEXT/PLAIN")
//                            || contentType.contains("mixed")
//                            || contentType.contains("text")) {
//                        //Here the risk of incomplete messages are shown, for messages that contain both
//                        //html and text content, but these messages are very rare;
//                        if (sb.length() == 0) {
//                            sb.append(bp.getContent().toString());
//                        }
//
//                        //here the attachments are handled
//                    } else if (contentType.toLowerCase().contains("application")) {
//                        MimeBodyPart mbp = (MimeBodyPart) bp;
//                        msgToRender.addAttachment(mbp);
//
//                        //Sometimes the text content of the message is encapsulated in another multipart,
//                        //so we have to iterate again through it.
//                    } else if (bp.getContentType().contains("multipart")) {
//                        Multipart mp2 = (Multipart) bp.getContent();
//                        for (int j = mp2.getCount() - 1; j >= 0; j--) {
//                            BodyPart bp2 = mp2.getBodyPart(i);
//                            if ((bp2.getContentType().contains("TEXT/HTML")
//                                    || bp2.getContentType().contains("TEXT/PLAIN"))) {
//                                sb.append(bp2.getContent().toString());
//                            }
//                        }
//                    }
//                }
//            }

        } catch (Exception e) {
        }
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                RenderMessage();
                return null;
            }
        };
    }
    /*
     call only once the message is loaded
     handle the info about attachments
     */

    private void showMessage() {
        msgRendererEngine.loadContent(sb.toString());
    }

    boolean hasAttachments(Message msg) throws MessagingException {
        if (msg.isMimeType("multipart/mixed")) {
            try {
                Multipart mp = (Multipart) msg.getContent();
                if (mp.getCount() > 1) {
                    return true;
                }
            } catch (IOException ex) {
                Logger.getLogger(MessageRendererService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Return the primary text content of the message.
     */
    private String getText(Part p) throws
            MessagingException, IOException {
        String messageType = p.getContentType();
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            return s;
        }
        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
//                if (messageType.contains("application")) {
//                    MimeBodyPart mbp = (MimeBodyPart) bp;
//                    msgToRender.addAttachment(mbp);
//                }
                
                if (bp.isMimeType("text/plain")) {
                    if (text == null) {
                        text = getText(bp);
                    }
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null) {
                        return s;
                    }
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) {
                    return s;
                }
            }
        }
        return null;
    }
}
