/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.controller.Services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
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
        Message msg = msgToRender.getMsgRef();
        try {
            String msgType = msg.getContentType();
            if (msgType.contains("TEXT/HTML")
                    || msgType.contains("TEXT/PLAIN")
                    || msgType.contains("text")) {
                sb.append(msg.getContent().toString());
            } else if (msgType.contains("multipart")) {
                Multipart mp = (Multipart) msg.getContent();
                for (int i = mp.getCount() - 1; i >= 0; i--) {
                    BodyPart bp = mp.getBodyPart(i);
                    String contentType = bp.getContentType();
                    if (contentType.contains("TEXT/HTML")
                            || contentType.contains("TEXT/PLAIN")
                            || contentType.contains("text")
                            || contentType.contains("mixed")) {
                        if (sb.length() == 0) {
                            sb.append(bp.getContent().toString());
                        }
                    } else if (contentType.toLowerCase().contains("application")) {
                        MimeBodyPart mbp = (MimeBodyPart) bp;
                    } else if (bp.getContentType().contains("multipart")) {
                        Multipart mp2 = (Multipart) bp.getContent();
                        for (int j = mp2.getCount() - 1; j >= 0; j--) {
                            BodyPart bp2 = mp2.getBodyPart(j);
                            if (bp2.getContentType().contains("TEXT/HTML")
                                    || bp2.getContentType().contains("TEXT/PLAIN")
                                    || bp2.getContentType().contains("text")
                                    || bp2.getContentType().contains("mixed")) {

                                sb.append(bp2.getContent().toString());
                            }
                        }
                    }
                }
            }
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
}
