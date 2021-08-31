package io.github.chris2011.netbeans.minifierbeans.folder;

import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsPanelController;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Chris
 */
public class FolderOptionsModel {
    private static FolderOptionsModel instance;

    public static FolderOptionsModel getDefault () {
        if (instance == null) {
            instance = new FolderOptionsModel();
        }
        return instance;
    }
    
    private static Preferences getPreferences() {
        return NbPreferences.forModule(MinificationOptionsPanelController.class);
    }

    /**
     * @return the createSeparateBuildFolder
     */
    public Boolean getCreateSeparateBuildFolderOption() {
        return getPreferences().getBoolean("createSeparateBuildFolder", true);
    }
    
    /**
     * @param createSeparateBuildFolder the createSeparateBuildFolder to set
     */
    public void setCreateSeparateBuildFolderOption(Boolean createSeparateBuildFolder) {
        getPreferences().putBoolean("createSeparateBuildFolder", createSeparateBuildFolder);
    }

    /**
     * @return the fileExtensionsRegex
     */
    public String getFileExtensionsRegexOption() {
        return getPreferences().get("fileExtensionsRegex", "css|json|js|xml|html|htm");
    }
    
    /**
     * @param fileExtensionsRegex the fileExtensionsRegex to set
     */
    public void setFileExtensionsRegexOption(String fileExtensionsRegex) {
        getPreferences().put("fileExtensionsRegex", fileExtensionsRegex);
    }

    /**
     * @return the minifyAllFiles
     */
    public Boolean getMinifyAllFilesOption() {
        return getPreferences().getBoolean("minifyAllFiles", false);
    }
    
    /**
     * @param minifyAllFiles the minifyAllFiles to set
     */
    public void setMinifyAllFilesOption(Boolean minifyAllFiles) {
        getPreferences().putBoolean("minifyAllFiles", minifyAllFiles);
    }

    /**
     * @return the addLogToFile
     */
    public Boolean getAddLogToFileOption() {
        return getPreferences().getBoolean("addLogToFile", false);
    }
    
    /**
     * @param addLogToFile the addLogToFile to set
     */
    public void setAddLogToFileOption(Boolean addLogToFile) {
        getPreferences().putBoolean("addLogToFile", addLogToFile);
    }

    /**
     * @return the enableOutputLogAlert
     */
    public Boolean getEnableOutputLogAlertOption() {
        return getPreferences().getBoolean("enableOutputLogAlert", true);
    }
    
    /**
     * @param enableOutputLogAlert the enableOutputLogAlert to set
     */
    public void setEnableOutputLogAlertOption(Boolean enableOutputLogAlert) {
        getPreferences().putBoolean("enableOutputLogAlert", enableOutputLogAlert);
    }

    /**
     * @return the enableShortKeyAction
     */
    public Boolean getEnableShortKeyActionOption() {
        return getPreferences().getBoolean("enableShortKeyAction", false);
    }
    
    /**
     * @param enableShortKeyAction the enableShortKeyAction to set
     */
    public void setEnableShortKeyActionOption(Boolean enableShortKeyAction) {
        getPreferences().putBoolean("enableShortKeyAction", enableShortKeyAction);
    }

    /**
     * @return the enableShortKeyActionConfirmBox
     */
    public Boolean getEnableShortKeyActionConfirmBoxOption() {
        return getPreferences().getBoolean("enableShortKeyActionConfirmBox", false);
    }
    
    /**
     * @param enableShortKeyActionConfirmBox the enableShortKeyActionConfirmBox to set
     */
    public void setEnableShortKeyActionConfirmBoxOption(Boolean enableShortKeyActionConfirmBox) {
        getPreferences().putBoolean("enableShortKeyActionConfirmBox", enableShortKeyActionConfirmBox);
    }
}
