/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itsoftsolutions.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Inzimam
 */
public class SampleData {

    public static final ObservableList<EmailMessageBean> Inbox = FXCollections.observableArrayList(
            new EmailMessageBean("Hello from inzi", "ihtisham@gmail.com", -6, "<html>Hello its a long time now we did not arrang a get togather so <h3> I want to arrang a party for you all</h3></html>",false),
            new EmailMessageBean("Salam to all from Chand", "chand@gmail.com", 584, "<html>Hello dear how are you its a long time now I did not see your face <h3> can we meet in this week</h3></html>",false),
            new EmailMessageBean("Hello Anas!", "anas@gmail.com", 2648, "<html>Its the info message from <b>IT Soft Solutions</b> that we have a rich email client software. If you wanna buy let us know</html>",false),
            new EmailMessageBean("Hello from Naveed", "naveed@gmail.com", 1112965, "<html>Hi Naveed!<br> How is going your business? I hope its going good. We are planning to lauch our own email client software would you help us to market it? I hope you would do</html>",true),
            new EmailMessageBean("Hello from Rashid", "rashid@gmail.com", 21112965, "<html>Hi dear!<br> how are you I assume to catch you in the end of the week for shopping!</h3></html>",true)
    );
    public static final ObservableList<EmailMessageBean> Spam = FXCollections.observableArrayList(
            new EmailMessageBean("Hello from inzi", "dash@gmail.com", -6, "<html>Do you wanna get $1000 for free just click the link below to explore the way how?</html>",false),
            new EmailMessageBean("Hello from chand", "jewel@gmail.com", 584, "<html>Hello dear we are ready to deliver the $23000 to you if you make a sign up on our site</html>",false),
            new EmailMessageBean("Hello from Anas", "itsBam@gmail.com", 2648, "<html>Its the info message from <b>IT Soft Solutions</b> that we have a rich email client software at $20000. If you wanna buy let us know</html>",true),
            new EmailMessageBean("Hello from Naveed", "yadev@gmail.com", 1112965, "<html>Hi Inzimam!<br> How is going your business? I hope its going good. We are planning to lauch our own email client software would you help us to market it? I hope you would do</html>",true)
    );
    public static final ObservableList<EmailMessageBean> Sent = FXCollections.observableArrayList(
            new EmailMessageBean("Hello from inzi", "inzi769@gmail.com", -6, "<html>This is you first email sent by JavaFx Client sofware</html>",false),
            new EmailMessageBean("Hello from chand", "inzi769@gmail.com", 584, "<html>This is you second email sent by JavaFx Client sofware</html>",true),
            new EmailMessageBean("Hello from Anas", "anas@gmail.com", 2648, "<html>This is you Third try to send email  by JavaFx Client sofware</html>",true),
            new EmailMessageBean("Hello from Amir", "inzi769@gmail.com", 12890, "<html>These are the default values</html>",true),
            new EmailMessageBean("Hello from Naveed", "naveed@gmail.com", 1112965, "<html>Its not important email</html>",true),
            new EmailMessageBean("Hello from Rashid", "rashid@gmail.com", 21112965, "<html>This is latest email you have sent</html>",true),
            new EmailMessageBean("Hello from inzi", "inzi769@gmail.com", -6, "<html>This is you first email sent by JavaFx Client sofware</html>",true),
            new EmailMessageBean("Hello from chand", "inzi769@gmail.com", 584, "<html>This is you second email sent by JavaFx Client sofware</html>",true)
    );
    
}
