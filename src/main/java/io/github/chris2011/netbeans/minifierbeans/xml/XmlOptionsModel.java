package io.github.chris2011.netbeans.minifierbeans.xml;

import io.github.chris2011.netbeans.minifierbeans.ui.options.MinificationOptionsPanelController;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Chris
 */
public class XmlOptionsModel {
    private static XmlOptionsModel instance;

    public static XmlOptionsModel getDefault () {
        if (instance == null) {
            instance = new XmlOptionsModel();
        }
        return instance;
    }
    
    private static Preferences getPreferences() {
        return NbPreferences.forModule(MinificationOptionsPanelController.class);
    }

    /**
     * @return the xmlMinifierFlags
     */
    public String getXmlMinifierFlagsOption() {
        return getPreferences().get("xmlMinifierFlags", "--remove-comments true;");
    }
    
    /**
     * @param xmlMinifierFlags the xmlMinifierFlags to set
     */
    public void setXmlMinifierFlags(String xmlMinifierFlags) {
        getPreferences().put("xmlMinifierFlags", xmlMinifierFlags);
    }

    /**
     * @return the autoMinifyXml
     */
    public boolean getMinifyXmlOnSaveOption() {
        return getPreferences().getBoolean("minifyXmlOnSave", false);
    }

    /**
     * @param minifyXmlOnSave the autoMinifyXml to set
     */
    public void setMinifyXmlOnSaveOption(boolean minifyXmlOnSave) {
        getPreferences().putBoolean("minifyXmlOnSave", minifyXmlOnSave);
    }

    /**
     * @return the generateNewXmlFile
     */
    public boolean getGenerateNewXmlFileOption() {
        return getPreferences().getBoolean("generateNewXmlFile", true);
    }

    /**
     * @param generateNewXmlFile the generateNewXmlFile to set
     */
    public void setGenerateNewXmlFileOption(boolean generateNewXmlFile) {
        getPreferences().putBoolean("generateNewXmlFile", generateNewXmlFile);
    }

    /**
     * @return the xmlPreExtension
     */
    public String getXmlPreExtensionOption() {
        return getPreferences().get("xmlPreExtension", ".min");
    }

    /**
     * @param xmlPreExtension the xmlPreExtension to set
     */
    public void setXmlPreExtensionOption(String xmlPreExtension) {
        getPreferences().put("xmlPreExtension", xmlPreExtension);
    }

    /**
     * @return the xmlHeader
     */
    public String getXmlHeaderOption() {
        return getPreferences().get("xmlHeader", "");
    }

    /**
     * @param xmlHeader the xmlHeader to set
     */
    public void setXmlHeaderOption(String xmlHeader) {
        getPreferences().put("xmlHeader", xmlHeader);
    }
}
