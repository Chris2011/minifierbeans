package io.github.chris2011.netbeans.minifierbeans.css;

import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsPanelController;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Chris
 */
public class CssOptionsModel {
    private static CssOptionsModel instance;

    public static CssOptionsModel getDefault () {
        if (instance == null) {
            instance = new CssOptionsModel();
        }
        return instance;
    }
    
    private static Preferences getPreferences() {
        return NbPreferences.forModule(MinificationOptionsPanelController.class);
    }

    /**
     * @return the minifyCssOnSave
     */
    public boolean getMinifyCssOnSaveOption() {
        return getPreferences().getBoolean("minifyCssOnSave", false);
    }

    /**
     * @param minifyCssOnSave the minifyCssOnSave to set
     */
    public void setMinifyCssOnSaveOption(boolean minifyCssOnSave) {
        getPreferences().putBoolean("minifyCssOnSave", minifyCssOnSave);
    }

    /**
     * @return the newCSSFile
     */
    public boolean getGenerateNewCssFileOption() {
        return getPreferences().getBoolean("generateNewCssFile", true);
    }

    /**
     * @param newCssFile the newCssFile to set
     */
    public void setGenerateNewCssFileOption(boolean generateNewCssFile) {
        getPreferences().putBoolean("generateNewCssFile", generateNewCssFile);
    }

    /**
     * @return the cssPreExtension
     */
    public String getCssPreExtensionOption() {
        return getPreferences().get("preExtensionCss", ".min");
    }

    /**
     * @param cssPreExtension the cssPreExtension to set
     */
    public void setCssPreExtensionOption(String cssPreExtension) {
        getPreferences().put("cssPreExtension", cssPreExtension);
    }

    /**
     * @return the cssHeader
     */
    public String getCssHeaderOption() {
        return getPreferences().get("cssHeader", "");
    }

    /**
     * @param cssHeader the cssHeader to set
     */
    public void setCssHeaderOption(String cssHeader) {
        getPreferences().put("cssHeader", cssHeader);
    }
}
