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
package org.netbeans.util.source.minify;


public class MinifyResult {
    private Integer directories;
    private Integer cssFiles;
    private Integer jsFiles;
    private Integer htmlFiles;
    
    private long inputJsFilesSize;
    private long outputJsFilesSize;
    private long inputCssFilesSize;
    private long outputCssFilesSize;
    private long inputHtmlFilesSize;
    private long outputHtmlFilesSize;

    /**
     * @return the directories
     */
    public Integer getDirectories() {
        return directories;
    }

    /**
     * @param directories the directories to set
     */
    public void setDirectories(Integer directories) {
        this.directories = directories;
    }

    /**
     * @return the cssFiles
     */
    public Integer getCssFiles() {
        return cssFiles;
    }

    /**
     * @param cssFiles the cssFiles to set
     */
    public void setCssFiles(Integer cssFiles) {
        this.cssFiles = cssFiles;
    }

    /**
     * @return the jsFiles
     */
    public Integer getJsFiles() {
        return jsFiles;
    }

    /**
     * @param jsFiles the jsFiles to set
     */
    public void setJsFiles(Integer jsFiles) {
        this.jsFiles = jsFiles;
    }

    /**
     * @return the inputJsFilesSize
     */
    public long getInputJsFilesSize() {
        return inputJsFilesSize;
    }

    /**
     * @param inputJsFilesSize the inputJsFilesSize to set
     */
    public void setInputJsFilesSize(long inputJsFilesSize) {
        this.inputJsFilesSize = inputJsFilesSize;
    }

    /**
     * @return the outputJsFilesSize
     */
    public long getOutputJsFilesSize() {
        return outputJsFilesSize;
    }

    /**
     * @param outputJsFilesSize the outputJsFilesSize to set
     */
    public void setOutputJsFilesSize(long outputJsFilesSize) {
        this.outputJsFilesSize = outputJsFilesSize;
    }

    /**
     * @return the inputCssFilesSize
     */
    public long getInputCssFilesSize() {
        return inputCssFilesSize;
    }

    /**
     * @param inputCssFilesSize the inputCssFilesSize to set
     */
    public void setInputCssFilesSize(long inputCssFilesSize) {
        this.inputCssFilesSize = inputCssFilesSize;
    }

    /**
     * @return the outputCssFilesSize
     */
    public long getOutputCssFilesSize() {
        return outputCssFilesSize;
    }

    /**
     * @param outputCssFilesSize the outputCssFilesSize to set
     */
    public void setOutputCssFilesSize(long outputCssFilesSize) {
        this.outputCssFilesSize = outputCssFilesSize;
    }

    /**
     * @return the htmlFiles
     */
    public Integer getHtmlFiles() {
        return htmlFiles;
    }

    /**
     * @param htmlFiles the htmlFiles to set
     */
    public void setHtmlFiles(Integer htmlFiles) {
        this.htmlFiles = htmlFiles;
    }

    /**
     * @return the inputHtmlFilesSize
     */
    public long getInputHtmlFilesSize() {
        return inputHtmlFilesSize;
    }

    /**
     * @param inputHtmlFilesSize the inputHtmlFilesSize to set
     */
    public void setInputHtmlFilesSize(long inputHtmlFilesSize) {
        this.inputHtmlFilesSize = inputHtmlFilesSize;
    }

    /**
     * @return the outputHtmlFilesSize
     */
    public long getOutputHtmlFilesSize() {
        return outputHtmlFilesSize;
    }

    /**
     * @param outputHtmlFilesSize the outputHtmlFilesSize to set
     */
    public void setOutputHtmlFilesSize(long outputHtmlFilesSize) {
        this.outputHtmlFilesSize = outputHtmlFilesSize;
    }
 
 
}
