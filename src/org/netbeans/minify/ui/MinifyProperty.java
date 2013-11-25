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
package org.netbeans.minify.ui;

import java.io.Serializable;


public class MinifyProperty implements Serializable {

    private Boolean newJSFile = true;
    private String preExtensionJS = "min";
    private Boolean jsObfuscate = true;//munge
    private Boolean preserveSemicolon = false;
    private Character separatorJS = '.';
    
    private Boolean newCSSFile = true;
    private String preExtensionCSS = "min";
     private Character separatorCSS = '.';
     
         private Boolean newHTMLFile = true;
    private String preExtensionHTML = "min";
     private Character separatorHTML = '.';
     
      private Boolean buildInternalJSMinify = true;
      private Boolean buildInternalCSSMinify = true;
     
     
    private Boolean separatBuild = false;
    
    
    private Boolean buildCSSMinify = true;
    private Boolean skipPreExtensionCSS = true;
    
    private Boolean buildJSMinify = true;
    private Boolean skipPreExtensionJS = true;
    
        private Boolean buildHTMLMinify = true;
    private Boolean skipPreExtensionHTML = true;
    
    private Boolean appendLogToFile = false;
    
     private String charset = "UTF-8";
    private Integer lineBreakPosition = -1;
    private Boolean verbose = false;
  private Boolean disableOptimizations = false;

    
   private static MinifyProperty uniqInstance;

  private MinifyProperty() {
  }

  public static synchronized MinifyProperty getInstance() {
  if (uniqInstance == null) {
        MinifyPropertyController minifyPropertyController = new MinifyPropertyController();
        uniqInstance = minifyPropertyController.readMinifyProperty();
    }
    return uniqInstance;
  }
  
  public static synchronized MinifyProperty getDefaultInstance() {
  if (uniqInstance == null) {
        uniqInstance = new MinifyProperty();
    }
    return uniqInstance;
  }
  
  
  
    
    
    
    
    /**
     * @return the newJSFile
     */
    public Boolean isNewJSFile() {
        return newJSFile;
    }

    /**
     * @param newJSFile the newJSFile to set
     */
    public void setNewJSFile(Boolean newJSFile) {
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
    public Boolean isJsObfuscate() {
        return jsObfuscate;
    }

    /**
     * @param jsObfuscate the jsObfuscate to set
     */
    public void setJsObfuscate(Boolean jsObfuscate) {
        this.jsObfuscate = jsObfuscate;
    }

    /**
     * @return the preserveSemicolon
     */
    public Boolean isPreserveSemicolon() {
        return preserveSemicolon;
    }

    /**
     * @param preserveSemicolon the preserveSemicolon to set
     */
    public void setPreserveSemicolon(Boolean preserveSemicolon) {
        this.preserveSemicolon = preserveSemicolon;
    }

    /**
     * @return the newCSSFile
     */
    public Boolean isNewCSSFile() {
        return newCSSFile;
    }

    /**
     * @param newCSSFile the newCSSFile to set
     */
    public void setNewCSSFile(Boolean newCSSFile) {
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
    public Boolean isSeparatBuild() {
        return separatBuild;
    }

    /**
     * @param separatBuild the separatBuild to set
     */
    public void setSeparatBuild(Boolean separatBuild) {
        this.separatBuild = separatBuild;
    }

    /**
     * @return the buildCSSMinify
     */
    public Boolean isBuildCSSMinify() {
        return buildCSSMinify;
    }

    /**
     * @param buildCSSMinify the buildCSSMinify to set
     */
    public void setBuildCSSMinify(Boolean buildCSSMinify) {
        this.buildCSSMinify = buildCSSMinify;
    }

    /**
     * @return the buildJSMinify
     */
    public Boolean isBuildJSMinify() {
        return buildJSMinify;
    }

    /**
     * @param buildJSMinify the buildJSMinify to set
     */
    public void setBuildJSMinify(Boolean buildJSMinify) {
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
    public Integer getLineBreakPosition() {
        return lineBreakPosition;
    }

    /**
     * @param lineBreakPosition the lineBreakPosition to set
     */
    public void setLineBreakPosition(Integer lineBreakPosition) {
        this.lineBreakPosition = lineBreakPosition;
    }

    /**
     * @return the verbose
     */
    public Boolean getVerbose() {
        return verbose;
    }

    /**
     * @param verbose the verbose to set
     */
    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * @return the disableOptimizations
     */
    public Boolean getDisableOptimizations() {
        return disableOptimizations;
    }

    /**
     * @param disableOptimizations the disableOptimizations to set
     */
    public void setDisableOptimizations(Boolean disableOptimizations) {
        this.disableOptimizations = disableOptimizations;
    }

    /**
     * @return the skipPreExtensionCSS
     */
    public Boolean isSkipPreExtensionCSS() {
        return skipPreExtensionCSS;
    }

    /**
     * @param skipPreExtensionCSS the skipPreExtensionCSS to set
     */
    public void setSkipPreExtensionCSS(Boolean skipPreExtensionCSS) {
        this.skipPreExtensionCSS = skipPreExtensionCSS;
    }

    /**
     * @return the skipPreExtensionJS
     */
    public Boolean isSkipPreExtensionJS() {
        return skipPreExtensionJS;
    }

    /**
     * @param skipPreExtensionJS the skipPreExtensionJS to set
     */
    public void setSkipPreExtensionJS(Boolean skipPreExtensionJS) {
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
    public Boolean isNewHTMLFile() {
        return newHTMLFile;
    }

    /**
     * @param newHTMLFile the newHTMLFile to set
     */
    public void setNewHTMLFile(Boolean newHTMLFile) {
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
    public Boolean isBuildHTMLMinify() {
        return buildHTMLMinify;
    }

    /**
     * @param buildHTMLMinify the buildHTMLMinify to set
     */
    public void setBuildHTMLMinify(Boolean buildHTMLMinify) {
        this.buildHTMLMinify = buildHTMLMinify;
    }

    /**
     * @return the skipPreExtensionHTML
     */
    public Boolean isSkipPreExtensionHTML() {
        return skipPreExtensionHTML;
    }

    /**
     * @param skipPreExtensionHTML the skipPreExtensionHTML to set
     */
    public void setSkipPreExtensionHTML(Boolean skipPreExtensionHTML) {
        this.skipPreExtensionHTML = skipPreExtensionHTML;
    }

    /**
     * @return the buildInternalJSMinify
     */
    public Boolean isBuildInternalJSMinify() {
        return buildInternalJSMinify;
    }

    /**
     * @param buildInternalJSMinify the buildInternalJSMinify to set
     */
    public void setBuildInternalJSMinify(Boolean buildInternalJSMinify) {
        this.buildInternalJSMinify = buildInternalJSMinify;
    }

    /**
     * @return the buildInternalCSSMinify
     */
    public Boolean isBuildInternalCSSMinify() {
        return buildInternalCSSMinify;
    }

    /**
     * @param buildInternalCSSMinify the buildInternalCSSMinify to set
     */
    public void setBuildInternalCSSMinify(Boolean buildInternalCSSMinify) {
        this.buildInternalCSSMinify = buildInternalCSSMinify;
    }

    /**
     * @return the appendLogToFile
     */
    public Boolean isAppendLogToFile() {
        return appendLogToFile;
    }

    /**
     * @param appendLogToFile the appendLogToFile to set
     */
    public void setAppendLogToFile(Boolean appendLogToFile) {
        this.appendLogToFile = appendLogToFile;
    }
}
