package org.itsoftsolutions.controller;

import java.util.ArrayList;
import java.util.List;
import javax.mail.Folder;
import org.itsoftsolutions.model.EmailMessageBean;
import org.itsoftsolutions.model.folder.EmailFolderBean;

/**
 *
 * @author Inzimam
 */
public class ModelAccess {

    private EmailMessageBean selectedMessage;

    public EmailMessageBean getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessageBean selectedMessage) {
        this.selectedMessage = selectedMessage;
    }
    
    private EmailFolderBean<String> selectedFolder;

    /**
     * @return the selectedFolder
     */
    public EmailFolderBean<String> getSelectedFolder() {
        return selectedFolder;
    }

    /**
     * @param selectedFolder the selectedFolder to set
     */
    public void setSelectedFolder(EmailFolderBean<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }
    
    private List<Folder> foldersList = new ArrayList<>();
    
    public List<Folder> getFoldersList(){
        return foldersList;
    }
    public void addFolder(Folder folder){
        foldersList.add(folder);
    }
}
