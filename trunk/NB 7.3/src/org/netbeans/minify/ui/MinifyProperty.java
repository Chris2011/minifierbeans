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
    private Boolean newCSSFile = true;
    private String preExtensionCSS = "min";
    private Boolean separatBuild = false;
    private Boolean buildCSSMinify = true;
    private Boolean skipPreExtensionCSS = true;
    
    private Boolean buildJSMinify = true;
    private Boolean skipPreExtensionJS = true;
    
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
}
