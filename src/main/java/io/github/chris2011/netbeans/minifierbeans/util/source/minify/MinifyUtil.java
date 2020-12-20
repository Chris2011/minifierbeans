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
package io.github.chris2011.netbeans.minifierbeans.util.source.minify;

import io.github.chris2011.netbeans.minifierbeans.json.JSONMinifyUtil;
import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.SourceFile;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.XmlCompressor;
import com.yahoo.platform.yui.compressor.CssCompressor;
import io.github.chris2011.netbeans.minifierbeans.css.CSSMinify;
import io.github.chris2011.netbeans.minifierbeans.javascript.JSMinify;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import io.github.chris2011.netbeans.minifierbeans.ui.MinifyProperty;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

public class MinifyUtil {
    MinifyResult minify(FileObject parentFile, MinifyProperty minifyProperty) {
        int directory = 0, cssFile = 0, jsFile = 0, htmlFile = 0, xmlFile = 0, jsonFile = 0;
        MinifyResult minifyResult = new MinifyResult();

        for (FileObject file : parentFile.getChildren()) {
            if (file.isFolder()) {
                directory++;
                MinifyResult preMinifyResult = minify(file, minifyProperty);

                directory += preMinifyResult.getDirectories();
                cssFile += preMinifyResult.getCssFiles();
                jsFile += preMinifyResult.getJsFiles();
                htmlFile += preMinifyResult.getHtmlFiles();
                xmlFile += preMinifyResult.getXmlFiles();
                jsonFile += preMinifyResult.getJsonFiles();

                minifyResult.setInputJsFilesSize(minifyResult.getInputJsFilesSize() + preMinifyResult.getInputJsFilesSize());
                minifyResult.setOutputJsFilesSize(minifyResult.getOutputJsFilesSize() + preMinifyResult.getOutputJsFilesSize());
                minifyResult.setInputCssFilesSize(minifyResult.getInputCssFilesSize() + preMinifyResult.getInputCssFilesSize());
                minifyResult.setOutputCssFilesSize(minifyResult.getOutputCssFilesSize() + preMinifyResult.getOutputCssFilesSize());
                minifyResult.setInputHtmlFilesSize(minifyResult.getInputHtmlFilesSize() + preMinifyResult.getInputHtmlFilesSize());
                minifyResult.setOutputHtmlFilesSize(minifyResult.getOutputHtmlFilesSize() + preMinifyResult.getOutputHtmlFilesSize());
                minifyResult.setInputXmlFilesSize(minifyResult.getInputXmlFilesSize() + preMinifyResult.getInputXmlFilesSize());
                minifyResult.setOutputXmlFilesSize(minifyResult.getOutputXmlFilesSize() + preMinifyResult.getOutputXmlFilesSize());
                minifyResult.setInputJsonFilesSize(minifyResult.getInputJsonFilesSize() + preMinifyResult.getInputJsonFilesSize());
                minifyResult.setOutputJsonFilesSize(minifyResult.getOutputJsonFilesSize() + preMinifyResult.getOutputJsonFilesSize());
            } else if (file.getExt().equalsIgnoreCase("js") && minifyProperty.isBuildJSMinify()) {
                jsFile++;

                Boolean allow = true;
                String inputFilePath = file.getPath();
                String outputFilePath;

                if (minifyProperty.isSkipPreExtensionJS() && minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
                    if (minifyProperty.getPreExtensionJS() != null && !minifyProperty.getPreExtensionJS().trim().isEmpty()
                            && file.getName().matches(".*" + Pattern.quote(minifyProperty.getPreExtensionJS()))) {
                        allow = false;
                    }
                }

                if (allow) {
                    try {
                        if (minifyProperty.isNewJSFile() && minifyProperty.getPreExtensionJS() != null && !minifyProperty.getPreExtensionJS().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getPreExtensionJS() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }
                        
                        MinifyFileResult minifyFileResult = compress(inputFilePath, "text/javascript", outputFilePath, minifyProperty);

                        minifyResult.setInputJsFilesSize(minifyResult.getInputJsFilesSize() + minifyFileResult.getInputFileSize());
                        minifyResult.setOutputJsFilesSize(minifyResult.getOutputJsFilesSize() + minifyFileResult.getOutputFileSize());
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            } else if (file.getExt().equalsIgnoreCase("css") && minifyProperty.isBuildCSSMinify()) {
                cssFile++;
                try {
                    Boolean allow = true;
                    String inputFilePath = file.getPath();
                    String outputFilePath;

                    if (minifyProperty.isSkipPreExtensionCSS() && minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
                        if (minifyProperty.getPreExtensionCSS() != null && !minifyProperty.getPreExtensionCSS().trim().isEmpty()
                                && file.getName().matches(".*" + Pattern.quote(minifyProperty.getPreExtensionCSS()))) {
                            allow = false;
                        }
                    }

                    if (allow) {
                        if (minifyProperty.isNewCSSFile() && minifyProperty.getPreExtensionCSS() != null && !minifyProperty.getPreExtensionCSS().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getPreExtensionCSS() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }

                        MinifyFileResult minifyFileResult = compress(inputFilePath, "text/css", outputFilePath, minifyProperty);

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
                        if (minifyProperty.getPreExtensionHTML() != null && !minifyProperty.getPreExtensionHTML().trim().isEmpty()
                                && file.getName().matches(".*" + Pattern.quote(minifyProperty.getPreExtensionHTML()))) {
                            allow = false;
                        }
                    }
                    if (allow) {
                        if (minifyProperty.isNewHTMLFile() && minifyProperty.getPreExtensionHTML() != null && !minifyProperty.getPreExtensionHTML().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getPreExtensionHTML() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }

                        MinifyFileResult minifyFileResult = compress(inputFilePath, "text/html", outputFilePath, minifyProperty);
                        minifyResult.setInputHtmlFilesSize(minifyResult.getInputHtmlFilesSize() + minifyFileResult.getInputFileSize());
                        minifyResult.setOutputHtmlFilesSize(minifyResult.getOutputHtmlFilesSize() + minifyFileResult.getOutputFileSize());
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("xml") && minifyProperty.isBuildXMLMinify()) {
                xmlFile++;
                try {
                    Boolean allow = true;
                    String inputFilePath = file.getPath();
                    String outputFilePath;

                    if (minifyProperty.isSkipPreExtensionXML() && minifyProperty.isBuildXMLMinify() && minifyProperty.isNewXMLFile()) {
                        if (minifyProperty.getPreExtensionXML() != null && !minifyProperty.getPreExtensionXML().trim().isEmpty()
                                && file.getName().matches(".*" + Pattern.quote(minifyProperty.getPreExtensionXML()))) {
                            allow = false;
                        }
                    }
                    if (allow) {
                        if (minifyProperty.isNewXMLFile() && minifyProperty.getPreExtensionXML() != null && !minifyProperty.getPreExtensionXML().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getPreExtensionXML() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }

                        MinifyFileResult minifyFileResult = compress(inputFilePath, "text/xml-mime", outputFilePath, minifyProperty);
                        minifyResult.setInputXmlFilesSize(minifyResult.getInputXmlFilesSize() + minifyFileResult.getInputFileSize());
                        minifyResult.setOutputXmlFilesSize(minifyResult.getOutputXmlFilesSize() + minifyFileResult.getOutputFileSize());
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("json") && minifyProperty.isBuildJSONMinify()) {
                jsonFile++;
                try {
                    Boolean allow = true;
                    String inputFilePath = file.getPath();
                    String outputFilePath;

                    if (minifyProperty.isSkipPreExtensionJSON() && minifyProperty.isBuildJSONMinify() && minifyProperty.isNewJSONFile()) {
                        if (minifyProperty.getPreExtensionJSON() != null && !minifyProperty.getPreExtensionJSON().trim().isEmpty()
                                && file.getName().matches(".*" + Pattern.quote(minifyProperty.getPreExtensionJSON()))) {
                            allow = false;
                        }
                    }
                    if (allow) {
                        if (minifyProperty.isNewJSONFile() && minifyProperty.getPreExtensionJSON() != null && !minifyProperty.getPreExtensionJSON().trim().isEmpty()) {
                            outputFilePath = file.getParent().getPath() + File.separator + file.getName() + minifyProperty.getPreExtensionJSON() + "." + file.getExt();
                        } else {
                            outputFilePath = inputFilePath;
                        }

                        MinifyFileResult minifyFileResult = compress(inputFilePath, "text/x-json", outputFilePath, minifyProperty);
                        minifyResult.setInputJsonFilesSize(minifyResult.getInputJsonFilesSize() + minifyFileResult.getInputFileSize());
                        minifyResult.setOutputJsonFilesSize(minifyResult.getOutputJsonFilesSize() + minifyFileResult.getOutputFileSize());
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }

        minifyResult.setDirectories(directory);
        minifyResult.setCssFiles(cssFile);
        minifyResult.setJsFiles(jsFile);
        minifyResult.setHtmlFiles(htmlFile);
        minifyResult.setXmlFiles(xmlFile);
        minifyResult.setJsonFiles(jsonFile);

        return minifyResult;
    }

    public String compressSelectedJavaScript(String inputFilename, String content, MinifyProperty minifyProperty) throws IOException {
        Compiler compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();

        compiler.initOptions(options);

        options.setStrictModeInput(false);
        options.setEmitUseStrict(false);
        options.setTrustedStrings(true);

        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        List<SourceFile> inputs = new ArrayList<>();
        inputs.add(SourceFile.fromCode(inputFilename, content));

        StringWriter outputWriter = new StringWriter();

        compiler.compile(CommandLineRunner.getDefaultExterns(), inputs, options);
        outputWriter.flush();

        return compiler.toSource();
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

    public void compressHtmlInternal(Reader in, Writer out, MinifyProperty minifyProperty) throws IOException {
        try {
            HtmlCompressor compressor = new HtmlCompressor();
            compressor.setRemoveIntertagSpaces(true);
//            compressor.setCompressCss(minifyProperty.isBuildInternalCSSMinify());               //compress inline css
//            compressor.setCompressJavaScript(minifyProperty.isBuildInternalJSMinify());
            String output = compressor.compress(fromStream(in));//out, minifyProperty.getLineBreakPosition());
            in.close();
            in = null;
            out.write(output);
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public MinifyFileResult compressContent(String inputFilename, String content, String mimeType, String outputFilename, MinifyProperty minifyProperty) throws IOException {
        Writer out = null;
        MinifyFileResult minifyFileResult = new MinifyFileResult();
        Compiler compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();

//        options.setLanguageOut(CompilerOptions.LanguageMode.ECMASCRIPT3);
        compiler.initOptions(options);

//        options.setStrictModeInput(false);
        options.setEmitUseStrict(false);
        options.setTrustedStrings(true);

        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        List<SourceFile> inputs = new ArrayList<>();
        inputs.add(SourceFile.fromCode(inputFilename, content));

        try {
            File outputFile = new File(outputFilename);
            out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());

            String output;
            switch (mimeType) {
                case "text/html":
                    HtmlCompressor htmlCompressor = new HtmlCompressor();

                    htmlCompressor.setRemoveIntertagSpaces(true);
//                    htmlCompressor.setCompressCss(minifyProperty.isBuildInternalCSSMinify());               //compress inline css
//                    htmlCompressor.setCompressJavaScript(minifyProperty.isBuildInternalJSMinify());        //compress inline javascript

                    output = htmlCompressor.compress(content);

                    if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderHTML())) {
                        out.write(output);
                    } else {
                        out.write(MinifyProperty.getInstance().getHeaderHTML() + "\n" + output);
                    }

                    break;
                case "text/javascript":
                    // TODO: Change to Google Closure Compiler.
                    compiler.compile(CommandLineRunner.getDefaultExterns(), inputs, options);

                    if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderJS())) {
                        out.write(compiler.toSource());
                    } else {
                        out.write(MinifyProperty.getInstance().getHeaderJS() + "\n" + compiler.toSource());
                    }
                    
                    break;
                case "text/css":
                    // TODO: Change to CSSNano.
                    Reader in = new StringReader(content);
                    CssCompressor cssCompressor = new CssCompressor(in);
                    in.close();
                    StringWriter outputWriter = new StringWriter();
                    cssCompressor.compress(outputWriter, minifyProperty.getLineBreakPosition());
                    outputWriter.flush();
                    if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderCSS())) {
                        out.write(outputWriter.toString());
                    } else {
                        out.write(MinifyProperty.getInstance().getHeaderCSS() + "\n" + outputWriter.toString());
                    }
                    outputWriter.close();

                    break;
                case "text/x-json":
                    JSONMinifyUtil jsonCompressor = new JSONMinifyUtil();
                    output = jsonCompressor.minify(content);

                    if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderJSON())) {
                        out.write(output);
                    } else {
                        out.write(MinifyProperty.getInstance().getHeaderJSON() + "\n" + output);
                    }

                    break;
                case "text/xml-mime":
                    XmlCompressor xmlCompressor = new XmlCompressor();

                    xmlCompressor.setRemoveIntertagSpaces(true);
                    xmlCompressor.setRemoveComments(true);
                    xmlCompressor.setEnabled(true);

                    output = xmlCompressor.compress(content);

                    if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderXML())) {
                        out.write(output);
                    } else {
                        out.write(MinifyProperty.getInstance().getHeaderXML() + "\n" + output);
                    }
                    
                    break;
                default:
                    break;
            }

            out.flush();
        } finally {
            IOUtils.closeQuietly(out);
        }
        return minifyFileResult;
    }

//    public MinifyFileResult compress(String inputFilename, String mimeType, String outputFilename, MinifyProperty minifyProperty) throws IOException {
    public MinifyFileResult compress(String inputFilename, String mimeType, String outputFilename, MinifyProperty minifyProperty) throws IOException {
        InputStreamReader in = null;
        Writer out = null;
        MinifyFileResult minifyFileResult = new MinifyFileResult();
//        Compiler compiler = new Compiler();
//        CompilerOptions options = new CompilerOptions();
        
//        compiler.initOptions(options);
        
//        options.setEmitUseStrict(false);
//        options.setTrustedStrings(true);
        
//        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        try {
            File inputFile = new File(inputFilename);
            File outputFile = new File(outputFilename);
            in = new InputStreamReader(new FileInputStream(inputFile), minifyProperty.getCharset());
            minifyFileResult.setInputFileSize(inputFile.length());
            out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());
            String output;

            switch (mimeType) {
                case "text/html":
                    HtmlCompressor htmlCompressor = new HtmlCompressor();
                    htmlCompressor.setRemoveIntertagSpaces(true);
//                    htmlCompressor.setCompressCss(minifyProperty.isBuildInternalCSSMinify());               //compress inline css
//                    htmlCompressor.setCompressJavaScript(minifyProperty.isBuildInternalJSMinify());        //compress inline javascript

                    output = htmlCompressor.compress(fromStream(in));
                    out.write(MinifyProperty.getInstance().getHeaderHTML() + "\n" + output);

                    break;
                case "text/javascript":
                    JSMinify.execute(null, FileUtil.toFileObject(inputFile), null, false);

                    if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderJS())) {
                        // TODO: Change
//                        out.write(compiler.toSource());
                    } else {
                        // TODO: Change
//                        out.write(MinifyProperty.getInstance().getHeaderJS() + "\n" + compiler.toSource());
                    }
                    
                    break;
                case "text/css":
                    // TODO: Change to CSSNano.
                    CSSMinify.execute(null, FileUtil.toFileObject(inputFile), null, false);
//                    CssCompressor cssCompressor = new CssCompressor(in);
//                    StringWriter outputWriter = new StringWriter();

//                    cssCompressor.compress(outputWriter, minifyProperty.getLineBreakPosition());
//                    outputWriter.flush();
                    if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderCSS())) {
                        // TODO: Change
//                        out.write(outputWriter.toString());
                    } else {
                        // TODO: Change
//                        out.write(MinifyProperty.getInstance().getHeaderCSS() + "\n" + outputWriter.toString());
                    }
//                    outputWriter.close();

                    break;
                case "text/x-json":
                    JSONMinifyUtil jsonCompressor = new JSONMinifyUtil();

                    output = jsonCompressor.minify(fromStream(in));
                    out.write(MinifyProperty.getInstance().getHeaderJSON() + "\n" + output);

                    break;
                case "text/xml-mime":
                    XmlCompressor xmlCompressor = new XmlCompressor();

                    xmlCompressor.setRemoveIntertagSpaces(true);
                    xmlCompressor.setRemoveComments(true);
                    xmlCompressor.setEnabled(true);

                    output = xmlCompressor.compress(fromStream(in));

                    out.write(MinifyProperty.getInstance().getHeaderXML() + "\n" + output);

                    break;
                default:
                    break;
            }

            in.close();
            in = null;

            out.flush();

            minifyFileResult.setOutputFileSize(outputFile.length());
            if (minifyProperty.isAppendLogToFile()) {
                out.append("\n<!--Size: " + minifyFileResult.getInputFileSize() + "=>"
                        + minifyFileResult.getOutputFileSize() + "Bytes "
                        + "\n Saved " + minifyFileResult.getSavedPercentage() + "%-->");
            }

        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return minifyFileResult;
    }

    public void compressXmlInternal(Reader in, Writer out, MinifyProperty minifyProperty) throws IOException {
        try {
            XmlCompressor compressor = new XmlCompressor();
            compressor.setRemoveIntertagSpaces(true);
            compressor.setRemoveComments(true);
            compressor.setEnabled(true);
            String output = compressor.compress(fromStream(in));//out, minifyProperty.getLineBreakPosition());
            in.close();
            in = null;
            out.write(output);
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public void compressJsonInternal(Reader in, Writer out, MinifyProperty minifyProperty) throws IOException {
        try {
            JSONMinifyUtil compressor = new JSONMinifyUtil();
            String output = compressor.minify(fromStream(in));
            in.close();
            in = null;
            out.write(output);
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public Boolean isMinifiedFile(String fileName, String preExt) {
        String extension = "";

        int extIndex = fileName.lastIndexOf(".");
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (extIndex > p) {
            extension = fileName.substring(extIndex+1);
            preExt = preExt + "." + extension;
        }
        
        extIndex = fileName.lastIndexOf(preExt);
        
        return extIndex > p;
    }

    public static String fromStream(Reader in) throws IOException {
        StringBuilder srcsb = new StringBuilder();
        int c;
        while ((c = in.read()) != -1) {
            srcsb.append((char) c);
        }

        return srcsb.toString();
    }
}
