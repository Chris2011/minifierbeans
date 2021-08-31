package io.github.chris2011.netbeans.minifierbeans.javascript;

import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsPanelController;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Chris
 */
public class JsOptionsModel {
    private static JsOptionsModel instance;

    public static JsOptionsModel getDefault () {
        if (instance == null) {
            instance = new JsOptionsModel();
        }
        return instance;
    }
    
    private static Preferences getPreferences() {
//        return NbPreferences.forModule(MiniferBeansPreference.class);
        return NbPreferences.forModule(MinificationOptionsPanelController.class);
    }

    /**
     * @return the closureCompilerFlags
     */
    public String getClosureCompilerFlagsOption() {
        return getPreferences().get("closureCompilerFlags", "--compilation_level SIMPLE; --language_in STABLE; --language_out ECMASCRIPT_NEXT");
    }
    
    /**
     * @return the closureCompilerFlags
     */
    public void setClosureCompilerFlagsOption(String closureCompilerFlags) {
        getPreferences().put("closureCompilerFlags", closureCompilerFlags);
    }

    /**
     * @return the minifyJsOnSave
     */
    public boolean getMinifyJsOnSaveOption() {
        return getPreferences().getBoolean("minifyJsOnSave", false);
    }

    /**
     * @param minifyJsOnSave the minifyJsOnSave to set
     */
    public void setMinifyJsOnSaveOption(boolean minifyJsOnSave) {
        getPreferences().putBoolean("minifyJsOnSave", minifyJsOnSave);
    }

    /**
     * @return the generateNewJsFile
     */
    public boolean getGenerateNewJsFileOption() {
        return getPreferences().getBoolean("generateNewJsFile", true);
    }

    /**
     * @param generateNewJsFile the generateNewJsFile to set
     */
    public void setGenerateNewJsFileOption(boolean generateNewJsFile) {
        getPreferences().putBoolean("generateNewJsFile", generateNewJsFile);
    }

    /**
     * @return the jsPreExtension
     */
    public String getJsPreExtensionOption() {
        return getPreferences().get("jsPreExtension", ".min");
    }

    /**
     * @param jsPreExtension the jsPreExtension to set
     */
    public void setJsPreExtensionOption(String jsPreExtension) {
        getPreferences().put("jsPreExtension", jsPreExtension);
    }

    /**
     * @return the jsHeader
     */
    public String getJsHeaderOption() {
        return getPreferences().get("jsHeader", "");
    }

    /**
     * @param jsHeader the jsHeader to set
     */
    public void setJsHeaderOption(String jsHeader) {
        getPreferences().put("jsHeader", jsHeader);
    }
}
