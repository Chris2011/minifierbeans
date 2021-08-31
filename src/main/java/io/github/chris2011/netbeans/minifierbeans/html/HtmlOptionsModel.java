package io.github.chris2011.netbeans.minifierbeans.html;

import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsPanelController;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Chris
 */
public class HtmlOptionsModel {
    private static HtmlOptionsModel instance;

    public static HtmlOptionsModel getDefault () {
        if (instance == null) {
            instance = new HtmlOptionsModel();
        }
        return instance;
    }
    
    private static Preferences getPreferences() {
        return NbPreferences.forModule(MinificationOptionsPanelController.class);
    }

    /**
     * @return the htmlMinifierFlags
     */
    public String getHtmlMinifierFlagsOption() {
        return getPreferences().get("htmlMinifierFlags", "--minify-js true; --minify-css true; --minify-urls true; --collapse-whitespace; --keep-closing-slash");
    }
    
    /**
     * @return the htmlMinifierFlags
     */
    public void setHtmlMinifierFlagsOption(String htmlMinifierFlags) {
        getPreferences().put("htmlMinifierFlags", htmlMinifierFlags);
    }

    /**
     * @return the minifyHtmlOnSave
     */
    public boolean getMinifyHtmlOnSaveOption() {
        return getPreferences().getBoolean("minifyHtmlOnSave", false);
    }

    /**
     * @param minifyHtmlOnSave the minifyHtmlOnSave to set
     */
    public void setMinifyHtmlOnSaveOption(boolean minifyHtmlOnSave) {
        getPreferences().putBoolean("minifyHtmlOnSave", minifyHtmlOnSave);
    }

    /**
     * @return the generateNewHtmlFile
     */
    public boolean getGenerateNewHtmlFileOption() {
        return getPreferences().getBoolean("generateNewHtmlFile", true);
    }

    /**
     * @param generateNewHtmlFile the generateNewHtmlFile to set
     */
    public void setGenerateNewHtmlFileOption(boolean generateNewHtmlFile) {
        getPreferences().putBoolean("generateNewHtmlFile", generateNewHtmlFile);
    }

    /**
     * @return the htmlPreExtension
     */
    public String getHtmlPreExtensionOption() {
        return getPreferences().get("htmlPreExtension", ".min");
    }

    /**
     * @param htmlPreExtension the htmlPreExtension to set
     */
    public void setHtmlPreExtensionOption(String htmlPreExtension) {
        getPreferences().put("htmlPreExtension", htmlPreExtension);
    }

    /**
     * @return the htmlHeader
     */
    public String getHtmlHeaderOption() {
        return getPreferences().get("htmlHeader", "");
    }

    /**
     * @param htmlHeader the htmlHeader to set
     */
    public void setHtmlHeaderOption(String htmlHeader) {
        getPreferences().put("htmlHeader", htmlHeader);
    }
}
