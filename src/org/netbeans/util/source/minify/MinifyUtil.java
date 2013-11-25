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

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.netbeans.minify.ui.MinifyProperty;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

public class MinifyUtil {

    MinifyResult minify(FileObject parentFile, MinifyProperty minifyProperty) {
        int directory = 0, cssFile = 0, jsFile = 0, htmlFile = 0;
        MinifyResult minifyResult = new MinifyResult();
        InputOutput io = IOProvider.getDefault().getIO(Bundle.CTL_Minify(), false);
        for (FileObject file : parentFile.getChildren()) {
            if (file.isFolder()) {
                directory++;
                MinifyResult preMinifyResult = minify(file, minifyProperty);
                directory = directory + preMinifyResult.getDirectories();
                cssFile = cssFile + preMinifyResult.getCssFiles();
                jsFile = jsFile + preMinifyResult.getJsFiles();
                htmlFile = htmlFile + preMinifyResult.getHtmlFiles();
                minifyResult.setInputJsFilesSize(minifyResult.getInputJsFilesSize() + preMinifyResult.getInputJsFilesSize());
                minifyResult.setOutputJsFilesSize(minifyResult.getOutputJsFilesSize() + preMinifyResult.getOutputJsFilesSize());
                minifyResult.setInputCssFilesSize(minifyResult.getInputCssFilesSize() + preMinifyResult.getInputCssFilesSize());
                minifyResult.setOutputCssFilesSize(minifyResult.getOutputCssFilesSize() + preMinifyResult.getOutputCssFilesSize());
                 minifyResult.setInputHtmlFilesSize(minifyResult.getInputHtmlFilesSize() + preMinifyResult.getInputHtmlFilesSize());
                minifyResult.setOutputHtmlFilesSize(minifyResult.getOutputHtmlFilesSize() + preMinifyResult.getOutputHtmlFilesSize());

            } else if (file.getExt().equalsIgnoreCase("js") && minifyProperty.isBuildJSMinify()) {
                jsFile++;
                try {
                    Boolean allow = true;
                    String inputFilePath = file.getPath();
                    String outputFilePath;

                    if (minifyProperty.isSkipPreExtensionJS() && minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
                         if (minifyProperty.getPreExtensionJS() != null && !minifyProperty.getPreExtensionJS().trim().isEmpty() &&
                                file.getName().matches(".*"+Pattern.quote(minifyProperty.getSeparatorJS() + minifyProperty.getPreExtensionJS()))) {
                            allow = false;
                        }
                    }
                    if (allow) {
                        if (minifyProperty.isNewJSFile() && minifyProperty.getPreExtensionJS() != null && !minifyProperty.getPreExtensionJS().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getSeparatorJS() + minifyProperty.getPreExtensionJS() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }


                        MinifyFileResult minifyFileResult = compressJavaScript(inputFilePath, outputFilePath, minifyProperty);
                        minifyResult.setInputJsFilesSize(minifyResult.getInputJsFilesSize() + minifyFileResult.getInputFileSize());
                        minifyResult.setOutputJsFilesSize(minifyResult.getOutputJsFilesSize() + minifyFileResult.getOutputFileSize());
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("css") && minifyProperty.isBuildCSSMinify()) {
                cssFile++;
                try {
                    Boolean allow = true;
                    String inputFilePath = file.getPath();
                    String outputFilePath;

                    if (minifyProperty.isSkipPreExtensionCSS() && minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
                       // String postFix = file.getName().substring(file.getName().lastIndexOf(minifyProperty.getSeparatorCSS()) + 1, file.getName().length());
                        if (minifyProperty.getPreExtensionCSS() != null && !minifyProperty.getPreExtensionCSS().trim().isEmpty() && 
                                 file.getName().matches(".*"+Pattern.quote(minifyProperty.getSeparatorCSS() + minifyProperty.getPreExtensionCSS()))) {
                            allow = false;
                        } 
                    }
                    if (allow) {
                        if (minifyProperty.isNewCSSFile() && minifyProperty.getPreExtensionCSS() != null && !minifyProperty.getPreExtensionCSS().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getSeparatorCSS() + minifyProperty.getPreExtensionCSS() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }

                        MinifyFileResult minifyFileResult = compressCss(inputFilePath, outputFilePath, minifyProperty);
                        minifyResult.setInputCssFilesSize(minifyResult.getInputCssFilesSize() + minifyFileResult.getInputFileSize());
                        minifyResult.setOutputCssFilesSize(minifyResult.getOutputCssFilesSize() + minifyFileResult.getOutputFileSize());
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("html") && minifyProperty.isBuildHTMLMinify()) {
                htmlFile++;
                try {
                    Boolean allow = true;
                    String inputFilePath = file.getPath();
                    String outputFilePath;

                    if (minifyProperty.isSkipPreExtensionHTML() && minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
                       // String postFix = file.getName().substring(file.getName().lastIndexOf(minifyProperty.getSeparatorHTML()) + 1, file.getName().length());
                        if (minifyProperty.getPreExtensionHTML() != null && !minifyProperty.getPreExtensionHTML().trim().isEmpty() && 
                                 file.getName().matches(".*"+Pattern.quote(minifyProperty.getSeparatorHTML() + minifyProperty.getPreExtensionHTML()))) {
                            allow = false;
                        } 
                    }
                    if (allow) {
                        if (minifyProperty.isNewHTMLFile() && minifyProperty.getPreExtensionHTML() != null && !minifyProperty.getPreExtensionHTML().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getSeparatorHTML() + minifyProperty.getPreExtensionHTML() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }

                        MinifyFileResult minifyFileResult = compressHtml(inputFilePath, outputFilePath, minifyProperty);
                        minifyResult.setInputHtmlFilesSize(minifyResult.getInputHtmlFilesSize() + minifyFileResult.getInputFileSize());
                        minifyResult.setOutputHtmlFilesSize(minifyResult.getOutputHtmlFilesSize() + minifyFileResult.getOutputFileSize());
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else {
                //io.getOut().println("Invalid File: " + file.getPath());
            }
        }

        minifyResult.setDirectories(directory);
        minifyResult.setCssFiles(cssFile);
        minifyResult.setJsFiles(jsFile);
        minifyResult.setHtmlFiles(htmlFile);
        return minifyResult;
    }

    public MinifyFileResult compressJavaScript(String inputFilename, String outputFilename, MinifyProperty minifyProperty) throws IOException {
        Reader in = null;
        Writer out = null;
        MinifyFileResult minifyFileResult = new MinifyFileResult();
        try {
            File inputFile = new File(inputFilename);
            File outputFile = new File(outputFilename);
            in = new InputStreamReader(new FileInputStream(inputFile), minifyProperty.getCharset());
            minifyFileResult.setInputFileSize(inputFile.length());
           
            JavaScriptCompressor compressor = new JavaScriptCompressor(in, new MinifyUtil.CompressorErrorReporter());
            in.close();
            in = null;

            out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());
            compressor.compress(out, minifyProperty.getLineBreakPosition(), minifyProperty.isJsObfuscate(), minifyProperty.getVerbose(), minifyProperty.isPreserveSemicolon(), minifyProperty.getDisableOptimizations());
            out.flush();
             minifyFileResult.setOutputFileSize(outputFile.length());
            if(minifyProperty.isAppendLogToFile()){
             out.append("\n/*Size: " + minifyFileResult.getInputFileSize() + "->"
                    + minifyFileResult.getOutputFileSize() + "Bytes "
                    + "\n Saved " + minifyFileResult.getSavedPercentage() + "%*/");
            }
            out.flush();

        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return minifyFileResult;
    }

    public void compressJavaScriptInternal(Reader in, Writer out, MinifyProperty minifyProperty) throws IOException {
        try {
            JavaScriptCompressor compressor = new JavaScriptCompressor(in, new MinifyUtil.CompressorErrorReporter());
            in.close();
            in = null;
            compressor.compress(out, minifyProperty.getLineBreakPosition(), minifyProperty.isJsObfuscate(), minifyProperty.getVerbose(), minifyProperty.isPreserveSemicolon(), minifyProperty.getDisableOptimizations());
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public MinifyFileResult compressCss(String inputFilename, String outputFilename, MinifyProperty minifyProperty) throws IOException {
        Reader in = null;
        Writer out = null;
        MinifyFileResult minifyFileResult = new MinifyFileResult();
        try {
            File inputFile = new File(inputFilename);
            File outputFile = new File(outputFilename);
            in = new InputStreamReader(new FileInputStream(inputFile), minifyProperty.getCharset());
            minifyFileResult.setInputFileSize(inputFile.length());
            
            CssCompressor compressor = new CssCompressor(in);
            in.close();
            in = null;

            out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());
            compressor.compress(out, minifyProperty.getLineBreakPosition());
             out.flush();
             minifyFileResult.setOutputFileSize(outputFile.length());
            if(minifyProperty.isAppendLogToFile()){
             out.append("\n/*Size: " + minifyFileResult.getInputFileSize() + "->"
                    + minifyFileResult.getOutputFileSize() + "Bytes "
                    + "\n Saved " + minifyFileResult.getSavedPercentage() + "%*/");
            }
            out.flush();


        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return minifyFileResult;
    }

    public void compressCssInternal(Reader in, Writer out, MinifyProperty minifyProperty) throws IOException {
        try {
            CssCompressor compressor = new CssCompressor(in);
            in.close();
            in = null;
            compressor.compress(out, minifyProperty.getLineBreakPosition());
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
   
    
    
     public MinifyFileResult compressHtml(String inputFilename, String outputFilename, MinifyProperty minifyProperty) throws IOException {
        InputStreamReader in = null;
        Writer out = null;
        MinifyFileResult minifyFileResult = new MinifyFileResult();
        try {
            File inputFile = new File(inputFilename);
            File outputFile = new File(outputFilename);
            in = new InputStreamReader(new FileInputStream(inputFile), minifyProperty.getCharset());
            minifyFileResult.setInputFileSize(inputFile.length());
            
            HtmlCompressor compressor = new HtmlCompressor();
            compressor.setRemoveIntertagSpaces(true);
            compressor.setCompressCss(minifyProperty.isBuildInternalCSSMinify());               //compress inline css 
            compressor.setCompressJavaScript(minifyProperty.isBuildInternalJSMinify());        //compress inline javascript
            compressor.setYuiJsNoMunge(!minifyProperty.isJsObfuscate()); 
//            compressor.setRemoveQuotes(true); //false may in feature             //removes unnecessary tag attribute quotes
//compressor.setSimpleDoctype(true);   //false may in feature          //simplify existing doctype
//compressor.setRemoveComments(true); //false may in feature           //if false keeps HTML comments (default is true)
//compressor.setSimpleBooleanAttributes(true);  //false may in feature  //remove values from boolean tag attributes
//compressor.setPreserveLineBreaks(true);        //preserves original line breaks


             String output =compressor.compress(fromStream(in));//out, minifyProperty.getLineBreakPosition());
           
             in.close();
            in = null;
            
            out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());
            out.write(output);
            
            out.flush();
             minifyFileResult.setOutputFileSize(outputFile.length());
            if(minifyProperty.isAppendLogToFile()){
            out.append("\n<!--Size: " + minifyFileResult.getInputFileSize() + "=>"
                    + minifyFileResult.getOutputFileSize() + "Bytes "
                    + "\n Saved " + minifyFileResult.getSavedPercentage() + "%-->");
            }
            out.flush();
          

        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return minifyFileResult;
    }

    public void compressHtmlInternal(Reader in, Writer out, MinifyProperty minifyProperty) throws IOException {
        try {
            HtmlCompressor compressor = new HtmlCompressor();
               compressor.setRemoveIntertagSpaces(true);
            compressor.setCompressCss(minifyProperty.isBuildInternalCSSMinify());               //compress inline css 
            compressor.setCompressJavaScript(minifyProperty.isBuildInternalJSMinify());    
            compressor.setYuiJsNoMunge(!minifyProperty.isJsObfuscate()); 
            String output =compressor.compress(fromStream(in));//out, minifyProperty.getLineBreakPosition());
             in.close();
            in = null;
            out.write(output);
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
   
    
    public static String fromStream(Reader in) throws IOException
{
    StringBuffer srcsb = new StringBuffer();
     int c;
        while ((c = in.read()) != -1) {
            srcsb.append((char) c);
        }
    
     return srcsb.toString();
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /* Error Reporter */
    
    
    private static final Logger logger = Logger.getLogger(MinifyUtil.class.getName());

    private static class CompressorErrorReporter implements ErrorReporter {

        @Override
        public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
            if (line < 0) {
                logger.log(Level.WARNING, message);
            } else {
                logger.log(Level.WARNING, line + ':' + lineOffset + ':' + message);
            }
        }

        @Override
        public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
            if (line < 0) {
                logger.log(Level.SEVERE, message);
            } else {
                logger.log(Level.SEVERE, line + ':' + lineOffset + ':' + message);
            }
        }

        @Override
        public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
            error(message, sourceName, line, lineSource, lineOffset);
            return new EvaluatorException(message);
        }
    }
}
