/**
 * Copyright [2013] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.minifierbeans.ui;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.prefs.Preferences;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

public class MinifyProperty implements Serializable {

    private boolean autoMinifyJS = false;
    private boolean autoMinifyCSS = false;
    private boolean autoMinifyHTML = false;
    private boolean autoMinifyXML = false;
    private boolean autoMinifyJSON = false;

    private String headerJS = "";
    private String headerCSS = "";
    private String headerHTML = "";
    private String headerXML = "";
    private String headerJSON = "";

    private boolean newJSFile = true;
    private String preExtensionJS = "min";
    private boolean jsObfuscate = true;//munge
    private boolean preserveSemicolon = false;
    private Character separatorJS = '.';
    private boolean newCSSFile = true;
    private String preExtensionCSS = "min";
    private Character separatorCSS = '.';
    private boolean newHTMLFile = true;
    private String preExtensionHTML = "min";
    private Character separatorHTML = '.';
    private boolean buildInternalJSMinify = true;
    private boolean buildInternalCSSMinify = true;
    private boolean newXMLFile = true;
    private String preExtensionXML = "min";
    private Character separatorXML = '.';
    private boolean newJSONFile = true;
    private String preExtensionJSON = "min";
    private Character separatorJSON = '.';

    private boolean separatBuild = false;
    private boolean buildJSMinify = true;
    private boolean skipPreExtensionJS = true;
    private boolean buildCSSMinify = true;
    private boolean skipPreExtensionCSS = true;
    private boolean buildHTMLMinify = true;
    private boolean skipPreExtensionHTML = true;
    private boolean buildXMLMinify = true;
    private boolean skipPreExtensionXML = true;
    private boolean buildJSONMinify = true;
    private boolean skipPreExtensionJSON = true;
    private boolean appendLogToFile = false;
    private boolean enableOutputLogAlert = true;
    private boolean enableShortKeyAction = false;
    private boolean enableShortKeyActionConfirmBox = false;
    private String charset = "UTF-8";
    private int lineBreakPosition = -1;
    private boolean verbose = false;
    private boolean disableOptimizations = false;
    private static MinifyProperty uniqueInstance;

    private MinifyProperty() {
    }

    public static synchronized MinifyProperty getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MinifyProperty();//MinifyPropertyController.getInstance().readMinifyProperty();
            uniqueInstance.load();
        }
        return uniqueInstance;
    }

    public void store() {
        Preferences prefs = NbPreferences.forModule(DummyCorePreference.class);
        prefs.put("separatorJS", separatorJS != null ? separatorJS.toString() : null);
        prefs.put("separatorCSS", separatorCSS != null ? separatorCSS.toString() : null);
        prefs.put("separatorHTML", separatorHTML != null ? separatorHTML.toString() : null);
        prefs.put("separatorXML", separatorXML != null ? separatorXML.toString() : null);
        prefs.put("separatorJSON", separatorJSON != null ? separatorJSON.toString() : null);

        Class<?> clazz = this.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            try {
                if (field.getType() == boolean.class) {
                    prefs.putBoolean(field.getName(), field.getBoolean(this));
                } else if (field.getType() == String.class) {
                    prefs.put(field.getName(), (String) field.get(this));
                } else if (field.getType() == int.class) {
                    prefs.putInt(field.getName(), field.getInt(this));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Exceptions.printStackTrace(ex);
            }

        }

    }

    public void load() {
        Preferences prefs = NbPreferences.forModule(DummyCorePreference.class);
        Class<?> clazz = this.getClass();
        separatorJS = prefs.get("separatorJS", separatorJS.toString()).toCharArray()[0];
        separatorCSS = prefs.get("separatorCSS", separatorCSS.toString()).toCharArray()[0];
        separatorHTML = prefs.get("separatorHTML", separatorHTML.toString()).toCharArray()[0];
        separatorXML = prefs.get("separatorXML", getSeparatorXML().toString()).toCharArray()[0];
        separatorJSON = prefs.get("separatorJSON", getSeparatorJSON().toString()).toCharArray()[0];

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == boolean.class) {
                try {
                    field.setBoolean(this, prefs.getBoolean(field.getName(), field.getBoolean(this)));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (field.getType() == String.class) {
                try {
                    field.set(this, prefs.get(field.getName(), (String) field.get(this)));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (field.getType() == int.class) {
                try {
                    prefs.putInt(field.getName(), field.getInt(this));
                    field.setInt(this, prefs.getInt(field.getName(), field.getInt(this)));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }
    }

    public void cancel() {
    }

    /**
     * @return the newJSFile
     */
    public boolean isNewJSFile() {
        return newJSFile;
    }

    /**
     * @param newJSFile the newJSFile to set
     */
    public void setNewJSFile(boolean newJSFile) {
        this.newJSFile = newJSFile;
    }

    /**
     * @return the preExtensionJS
     */
    public String getPreExtensionJS() {
        return preExtensionJS;
    }

    /**
     * @param preExtensionJS the preExtensionJS to set
     */
    public void setPreExtensionJS(String preExtensionJS) {
        this.preExtensionJS = preExtensionJS;
    }

    /**
     * @return the jsObfuscate
     */
    public boolean isJsObfuscate() {
        return jsObfuscate;
    }

    /**
     * @param jsObfuscate the jsObfuscate to set
     */
    public void setJsObfuscate(boolean jsObfuscate) {
        this.jsObfuscate = jsObfuscate;
    }

    /**
     * @return the preserveSemicolon
     */
    public boolean isPreserveSemicolon() {
        return preserveSemicolon;
    }

    /**
     * @param preserveSemicolon the preserveSemicolon to set
     */
    public void setPreserveSemicolon(boolean preserveSemicolon) {
        this.preserveSemicolon = preserveSemicolon;
    }

    /**
     * @return the newCSSFile
     */
    public boolean isNewCSSFile() {
        return newCSSFile;
    }

    /**
     * @param newCSSFile the newCSSFile to set
     */
    public void setNewCSSFile(boolean newCSSFile) {
        this.newCSSFile = newCSSFile;
    }

    /**
     * @return the preExtensionCSS
     */
    public String getPreExtensionCSS() {
        return preExtensionCSS;
    }

    /**
     * @param preExtensionCSS the preExtensionCSS to set
     */
    public void setPreExtensionCSS(String preExtensionCSS) {
        this.preExtensionCSS = preExtensionCSS;
    }

    /**
     * @return the separatBuild
     */
    public boolean isSeparatBuild() {
        return separatBuild;
    }

    /**
     * @param separatBuild the separatBuild to set
     */
    public void setSeparatBuild(boolean separatBuild) {
        this.separatBuild = separatBuild;
    }

    /**
     * @return the buildCSSMinify
     */
    public boolean isBuildCSSMinify() {
        return buildCSSMinify;
    }

    /**
     * @param buildCSSMinify the buildCSSMinify to set
     */
    public void setBuildCSSMinify(boolean buildCSSMinify) {
        this.buildCSSMinify = buildCSSMinify;
    }

    /**
     * @return the buildJSMinify
     */
    public boolean isBuildJSMinify() {
        return buildJSMinify;
    }

    /**
     * @param buildJSMinify the buildJSMinify to set
     */
    public void setBuildJSMinify(boolean buildJSMinify) {
        this.buildJSMinify = buildJSMinify;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * @return the lineBreakPosition
     */
    public int getLineBreakPosition() {
        return lineBreakPosition;
    }

    /**
     * @param lineBreakPosition the lineBreakPosition to set
     */
    public void setLineBreakPosition(int lineBreakPosition) {
        this.lineBreakPosition = lineBreakPosition;
    }

    /**
     * @return the verbose
     */
    public boolean getVerbose() {
        return verbose;
    }

    /**
     * @param verbose the verbose to set
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * @return the disableOptimizations
     */
    public boolean getDisableOptimizations() {
        return disableOptimizations;
    }

    /**
     * @param disableOptimizations the disableOptimizations to set
     */
    public void setDisableOptimizations(boolean disableOptimizations) {
        this.disableOptimizations = disableOptimizations;
    }

    /**
     * @return the skipPreExtensionCSS
     */
    public boolean isSkipPreExtensionCSS() {
        return skipPreExtensionCSS;
    }

    /**
     * @param skipPreExtensionCSS the skipPreExtensionCSS to set
     */
    public void setSkipPreExtensionCSS(boolean skipPreExtensionCSS) {
        this.skipPreExtensionCSS = skipPreExtensionCSS;
    }

    /**
     * @return the skipPreExtensionJS
     */
    public boolean isSkipPreExtensionJS() {
        return skipPreExtensionJS;
    }

    /**
     * @param skipPreExtensionJS the skipPreExtensionJS to set
     */
    public void setSkipPreExtensionJS(boolean skipPreExtensionJS) {
        this.skipPreExtensionJS = skipPreExtensionJS;
    }

    /**
     * @return the separatorJS
     */
    public Character getSeparatorJS() {
        return separatorJS;
    }

    /**
     * @param separatorJS the separatorJS to set
     */
    public void setSeparatorJS(Character separatorJS) {
        this.separatorJS = separatorJS;
    }

    /**
     * @return the separatorCSS
     */
    public Character getSeparatorCSS() {
        return separatorCSS;
    }

    /**
     * @param separatorCSS the separatorCSS to set
     */
    public void setSeparatorCSS(Character separatorCSS) {
        this.separatorCSS = separatorCSS;
    }

    /**
     * @return the newHTMLFile
     */
    public boolean isNewHTMLFile() {
        return newHTMLFile;
    }

    /**
     * @param newHTMLFile the newHTMLFile to set
     */
    public void setNewHTMLFile(boolean newHTMLFile) {
        this.newHTMLFile = newHTMLFile;
    }

    /**
     * @return the preExtensionHTML
     */
    public String getPreExtensionHTML() {
        return preExtensionHTML;
    }

    /**
     * @param preExtensionHTML the preExtensionHTML to set
     */
    public void setPreExtensionHTML(String preExtensionHTML) {
        this.preExtensionHTML = preExtensionHTML;
    }

    /**
     * @return the separatorHTML
     */
    public Character getSeparatorHTML() {
        return separatorHTML;
    }

    /**
     * @param separatorHTML the separatorHTML to set
     */
    public void setSeparatorHTML(Character separatorHTML) {
        this.separatorHTML = separatorHTML;
    }

    /**
     * @return the buildHTMLMinify
     */
    public boolean isBuildHTMLMinify() {
        return buildHTMLMinify;
    }

    /**
     * @param buildHTMLMinify the buildHTMLMinify to set
     */
    public void setBuildHTMLMinify(boolean buildHTMLMinify) {
        this.buildHTMLMinify = buildHTMLMinify;
    }

    /**
     * @return the skipPreExtensionHTML
     */
    public boolean isSkipPreExtensionHTML() {
        return skipPreExtensionHTML;
    }

    /**
     * @param skipPreExtensionHTML the skipPreExtensionHTML to set
     */
    public void setSkipPreExtensionHTML(boolean skipPreExtensionHTML) {
        this.skipPreExtensionHTML = skipPreExtensionHTML;
    }

    /**
     * @return the buildInternalJSMinify
     */
    public boolean isBuildInternalJSMinify() {
        return buildInternalJSMinify;
    }

    /**
     * @param buildInternalJSMinify the buildInternalJSMinify to set
     */
    public void setBuildInternalJSMinify(boolean buildInternalJSMinify) {
        this.buildInternalJSMinify = buildInternalJSMinify;
    }

    /**
     * @return the buildInternalCSSMinify
     */
    public boolean isBuildInternalCSSMinify() {
        return buildInternalCSSMinify;
    }

    /**
     * @param buildInternalCSSMinify the buildInternalCSSMinify to set
     */
    public void setBuildInternalCSSMinify(boolean buildInternalCSSMinify) {
        this.buildInternalCSSMinify = buildInternalCSSMinify;
    }

    /**
     * @return the appendLogToFile
     */
    public boolean isAppendLogToFile() {
        return appendLogToFile;
    }

    /**
     * @param appendLogToFile the appendLogToFile to set
     */
    public void setAppendLogToFile(boolean appendLogToFile) {
        this.appendLogToFile = appendLogToFile;
    }

    /**
     * @return the enableShortKeyAction
     */
    public boolean isEnableShortKeyAction() {
        return enableShortKeyAction;
    }

    /**
     * @param enableShortKeyAction the enableShortKeyAction to set
     */
    public void setEnableShortKeyAction(boolean enableShortKeyAction) {
        this.enableShortKeyAction = enableShortKeyAction;
    }

    /**
     * @return the enableShortKeyActionConfirmBox
     */
    public boolean isEnableShortKeyActionConfirmBox() {
        return enableShortKeyActionConfirmBox;
    }

    /**
     * @param enableShortKeyActionConfirmBox the enableShortKeyActionConfirmBox
     * to set
     */
    public void setEnableShortKeyActionConfirmBox(boolean enableShortKeyActionConfirmBox) {
        this.enableShortKeyActionConfirmBox = enableShortKeyActionConfirmBox;
    }

    /**
     * @return the enableOutputLogAlert
     */
    public boolean isEnableOutputLogAlert() {
        return enableOutputLogAlert;
    }

    /**
     * @param enableOutputLogAlert the enableOutputLogAlert to set
     */
    public void setEnableOutputLogAlert(boolean enableOutputLogAlert) {
        this.enableOutputLogAlert = enableOutputLogAlert;
    }

    /**
     * @return the newXMLFile
     */
    public boolean isNewXMLFile() {
        return newXMLFile;
    }

    /**
     * @param newXMLFile the newXMLFile to set
     */
    public void setNewXMLFile(boolean newXMLFile) {
        this.newXMLFile = newXMLFile;
    }

    /**
     * @return the preExtensionXML
     */
    public String getPreExtensionXML() {
        return preExtensionXML;
    }

    /**
     * @param preExtensionXML the preExtensionXML to set
     */
    public void setPreExtensionXML(String preExtensionXML) {
        this.preExtensionXML = preExtensionXML;
    }

    /**
     * @return the separatorXML
     */
    public Character getSeparatorXML() {
        return separatorXML;
    }

    /**
     * @param separatorXML the separatorXML to set
     */
    public void setSeparatorXML(Character separatorXML) {
        this.separatorXML = separatorXML;
    }

    /**
     * @return the newJSONFile
     */
    public boolean isNewJSONFile() {
        return newJSONFile;
    }

    /**
     * @param newJSONFile the newJSONFile to set
     */
    public void setNewJSONFile(boolean newJSONFile) {
        this.newJSONFile = newJSONFile;
    }

    /**
     * @return the preExtensionJSON
     */
    public String getPreExtensionJSON() {
        return preExtensionJSON;
    }

    /**
     * @param preExtensionJSON the preExtensionJSON to set
     */
    public void setPreExtensionJSON(String preExtensionJSON) {
        this.preExtensionJSON = preExtensionJSON;
    }

    /**
     * @return the separatorJSON
     */
    public Character getSeparatorJSON() {
        return separatorJSON;
    }

    /**
     * @param separatorJSON the separatorJSON to set
     */
    public void setSeparatorJSON(Character separatorJSON) {
        this.separatorJSON = separatorJSON;
    }

    /**
     * @return the buildXMLMinify
     */
    public boolean isBuildXMLMinify() {
        return buildXMLMinify;
    }

    /**
     * @param buildXMLMinify the buildXMLMinify to set
     */
    public void setBuildXMLMinify(boolean buildXMLMinify) {
        this.buildXMLMinify = buildXMLMinify;
    }

    /**
     * @return the skipPreExtensionXML
     */
    public boolean isSkipPreExtensionXML() {
        return skipPreExtensionXML;
    }

    /**
     * @param skipPreExtensionXML the skipPreExtensionXML to set
     */
    public void setSkipPreExtensionXML(boolean skipPreExtensionXML) {
        this.skipPreExtensionXML = skipPreExtensionXML;
    }

    /**
     * @return the buildJSONMinify
     */
    public boolean isBuildJSONMinify() {
        return buildJSONMinify;
    }

    /**
     * @param buildJSONMinify the buildJSONMinify to set
     */
    public void setBuildJSONMinify(boolean buildJSONMinify) {
        this.buildJSONMinify = buildJSONMinify;
    }

    /**
     * @return the skipPreExtensionJSON
     */
    public boolean isSkipPreExtensionJSON() {
        return skipPreExtensionJSON;
    }

    /**
     * @param skipPreExtensionJSON the skipPreExtensionJSON to set
     */
    public void setSkipPreExtensionJSON(boolean skipPreExtensionJSON) {
        this.skipPreExtensionJSON = skipPreExtensionJSON;
    }

    /**
     * @return the autoMinifyJS
     */
    public boolean isAutoMinifyJS() {
        return autoMinifyJS;
    }

    /**
     * @param autoMinifyJS the autoMinifyJS to set
     */
    public void setAutoMinifyJS(boolean autoMinifyJS) {
        this.autoMinifyJS = autoMinifyJS;
    }

    /**
     * @return the autoMinifyCSS
     */
    public boolean isAutoMinifyCSS() {
        return autoMinifyCSS;
    }

    /**
     * @param autoMinifyCSS the autoMinifyCSS to set
     */
    public void setAutoMinifyCSS(boolean autoMinifyCSS) {
        this.autoMinifyCSS = autoMinifyCSS;
    }

    /**
     * @return the autoMinifyHTML
     */
    public boolean isAutoMinifyHTML() {
        return autoMinifyHTML;
    }

    /**
     * @param autoMinifyHTML the autoMinifyHTML to set
     */
    public void setAutoMinifyHTML(boolean autoMinifyHTML) {
        this.autoMinifyHTML = autoMinifyHTML;
    }

    /**
     * @return the autoMinifyXML
     */
    public boolean isAutoMinifyXML() {
        return autoMinifyXML;
    }

    /**
     * @param autoMinifyXML the autoMinifyXML to set
     */
    public void setAutoMinifyXML(boolean autoMinifyXML) {
        this.autoMinifyXML = autoMinifyXML;
    }

    /**
     * @return the autoMinifyJSON
     */
    public boolean isAutoMinifyJSON() {
        return autoMinifyJSON;
    }

    /**
     * @param autoMinifyJSON the autoMinifyJSON to set
     */
    public void setAutoMinifyJSON(boolean autoMinifyJSON) {
        this.autoMinifyJSON = autoMinifyJSON;
    }

    /**
     * @return the headerJS
     */
    public String getHeaderJS() {
        return headerJS;
    }

    /**
     * @param headerJS the headerJS to set
     */
    public void setHeaderJS(String headerJS) {
        this.headerJS = headerJS;
    }

    /**
     * @return the headerCSS
     */
    public String getHeaderCSS() {
        return headerCSS;
    }

    /**
     * @param headerCSS the headerCSS to set
     */
    public void setHeaderCSS(String headerCSS) {
        this.headerCSS = headerCSS;
    }

    /**
     * @return the headerHTML
     */
    public String getHeaderHTML() {
        return headerHTML;
    }

    /**
     * @param headerHTML the headerHTML to set
     */
    public void setHeaderHTML(String headerHTML) {
        this.headerHTML = headerHTML;
    }

    /**
     * @return the headerXML
     */
    public String getHeaderXML() {
        return headerXML;
    }

    /**
     * @param headerXML the headerXML to set
     */
    public void setHeaderXML(String headerXML) {
        this.headerXML = headerXML;
    }

    /**
     * @return the headerJSON
     */
    public String getHeaderJSON() {
        return headerJSON;
    }

    /**
     * @param headerJSON the headerJSON to set
     */
    public void setHeaderJSON(String headerJSON) {
        this.headerJSON = headerJSON;
    }
}
