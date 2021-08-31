package io.github.chris2011.netbeans.minifierbeans.json;

import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsPanelController;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Chris
 */
public class JsonOptionsModel {
    private static JsonOptionsModel instance;

    public static JsonOptionsModel getDefault () {
        if (instance == null) {
            instance = new JsonOptionsModel();
        }
        return instance;
    }
    
    private static Preferences getPreferences() {
        return NbPreferences.forModule(MinificationOptionsPanelController.class);
    }

    /**
     * @return the minifyJsonOnSave
     */
    public boolean getMinifyJsonOnSaveOption() {
        return getPreferences().getBoolean("minifyJsonOnSave", false);
    }

    /**
     * @param minifyJsonOnSave the minifyJsonOnSave to set
     */
    public void setMinifyJsonOnSaveOption(boolean minifyJsonOnSave) {
        getPreferences().putBoolean("minifyJsonOnSave", minifyJsonOnSave);
    }

    /**
     * @return the generateNewJsonFile
     */
    public boolean getGenerateNewJsonFileOption() {
        return getPreferences().getBoolean("generateNewJsonFile", true);
    }

    /**
     * @param generateNewJsonFile the generateNewJsonFile to set
     */
    public void setGenerateNewJsonFileOption(boolean generateNewJsonFile) {
        getPreferences().putBoolean("generateNewJsonFile", generateNewJsonFile);
    }

    /**
     * @return the preExtensionJson
     */
    public String getJsonPreExtensionOption() {
        return getPreferences().get("jsonPreExtension", ".min");
    }

    /**
     * @param jsonPreExtension the jsonPreExtension to set
     */
    public void setJsonPreExtensionOption(String jsonPreExtension) {
        getPreferences().put("jsonPreExtension", jsonPreExtension);
    }

    /**
     * @return the jsonHeader
     */
    public String getJsonHeaderOption() {
        return getPreferences().get("jsonHeader", "");
    }

    /**
     * @param jsonHeader the jsonHeader to set
     */
    public void setJsonHeaderOption(String jsonHeader) {
        getPreferences().put("jsonHeader", jsonHeader);
    }
}
