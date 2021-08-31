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

import io.github.chris2011.netbeans.minifierbeans.json.JsonMinifyUtil;
import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.SourceFile;
import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.XmlCompressor;
import com.yahoo.platform.yui.compressor.CssCompressor;
import io.github.chris2011.netbeans.minifierbeans.css.CssOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.html.HtmlOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.javascript.JsOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.json.JsonOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.folder.FolderOptionsModel;
import io.github.chris2011.netbeans.minifierbeans.util.FileUtils;
import io.github.chris2011.netbeans.minifierbeans.xml.XmlOptionsModel;
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
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;

public class MinifyUtil {
    private final CssOptionsModel cssOptionsModel = CssOptionsModel.getDefault();
    private final HtmlOptionsModel htmlOptionsModel = HtmlOptionsModel.getDefault();
    private final JsOptionsModel jsOptionsModel = JsOptionsModel.getDefault();
    private final JsonOptionsModel jsonOptionsModel = JsonOptionsModel.getDefault();
    private final XmlOptionsModel xmlOptionsModel = XmlOptionsModel.getDefault();
    private final FolderOptionsModel folderOptionsModel = FolderOptionsModel.getDefault();

    MinifyResult minify(FileObject sourceFolder, FileObject targetFolder) {
        int directory = 0, cssFile = 0, jsFile = 0, htmlFile = 0, xmlFile = 0, jsonFile = 0;
        MinifyResult minifyResult = new MinifyResult();

        for (FileObject file : sourceFolder.getChildren()) {
            if (file.isFolder()) {
                try {
                    directory++;

                    MinifyResult preMinifyResult = minify(file, FileUtil.toFileObject(FileUtils.getTargetFolder(new File(targetFolder.getPath() + File.separator + file.getName() + "_minified"))));
                    directory = directory + preMinifyResult.getDirectories();
                    cssFile = cssFile + preMinifyResult.getCssFiles();
                    jsFile = jsFile + preMinifyResult.getJsFiles();
                    htmlFile = htmlFile + preMinifyResult.getHtmlFiles();
                    xmlFile = xmlFile + preMinifyResult.getXmlFiles();
                    jsonFile = jsonFile + preMinifyResult.getJsonFiles();
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
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("js") && folderOptionsModel.getFileExtensionsRegexOption().contains(file.getExt().toLowerCase())) {
                jsFile++;

                try {
                    String inputFilePath = file.getPath();
                    String outputFilePath = targetFolder.getPath();
                    boolean minifyAllFiles = folderOptionsModel.getMinifyAllFilesOption();

                    if (minifyAllFiles) {
                        if (jsOptionsModel.getGenerateNewJsFileOption()) {
                            outputFilePath += File.separator + file.getName() + jsOptionsModel.getJsPreExtensionOption() + "." + file.getExt();
                        } else {
                            outputFilePath += File.separator + file.getNameExt();
                        }
                    } else {
                        if (!file.getName().matches(".*" + Pattern.quote(jsOptionsModel.getJsPreExtensionOption()))) {
                            if (jsOptionsModel.getGenerateNewJsFileOption()) {
                                outputFilePath += File.separator + file.getName() + jsOptionsModel.getJsPreExtensionOption() + "." + file.getExt();
                            } else {
                                outputFilePath += File.separator + file.getNameExt();
                            }
                        }
                    }

                    MinifyFileResult minifyFileResult = compress(inputFilePath, "text/javascript", outputFilePath);
                    minifyResult.setInputJsFilesSize(minifyResult.getInputJsFilesSize() + minifyFileResult.getInputFileSize());
                    minifyResult.setOutputJsFilesSize(minifyResult.getOutputJsFilesSize() + minifyFileResult.getOutputFileSize());
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("css") && folderOptionsModel.getFileExtensionsRegexOption().contains(file.getExt().toLowerCase())) {
                cssFile++;

                try {
                    String inputFilePath = file.getPath();
                    String outputFilePath = targetFolder.getPath();
                    boolean minifyAllFiles = folderOptionsModel.getMinifyAllFilesOption();

                    if (minifyAllFiles) {
                        if (cssOptionsModel.getGenerateNewCssFileOption()) {
                            outputFilePath += File.separator + file.getName() + cssOptionsModel.getCssPreExtensionOption() + "." + file.getExt();
                        } else {
                            outputFilePath += File.separator + file.getNameExt();
                        }
                    } else {
                        if (!file.getName().matches(".*" + Pattern.quote(cssOptionsModel.getCssPreExtensionOption()))) {
                            if (cssOptionsModel.getGenerateNewCssFileOption()) {
                                outputFilePath += File.separator + file.getName() + cssOptionsModel.getCssPreExtensionOption() + "." + file.getExt();
                            } else {
                                outputFilePath += File.separator + file.getNameExt();
                            }
                        }
                    }

                    MinifyFileResult minifyFileResult = compress(inputFilePath, "text/css", outputFilePath);
                    minifyResult.setInputCssFilesSize(minifyResult.getInputCssFilesSize() + minifyFileResult.getInputFileSize());
                    minifyResult.setOutputCssFilesSize(minifyResult.getOutputCssFilesSize() + minifyFileResult.getOutputFileSize());
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("html") && folderOptionsModel.getFileExtensionsRegexOption().contains(file.getExt().toLowerCase())) {
                htmlFile++;

                try {
                    String inputFilePath = file.getPath();
                    String outputFilePath = targetFolder.getPath();
                    boolean minifyAllFiles = folderOptionsModel.getMinifyAllFilesOption();

                    if (minifyAllFiles) {
                        if (htmlOptionsModel.getGenerateNewHtmlFileOption()) {
                            outputFilePath += File.separator + file.getName() + htmlOptionsModel.getHtmlPreExtensionOption() + "." + file.getExt();
                        } else {
                            outputFilePath += File.separator + file.getNameExt();
                        }
                    } else {
                        if (!file.getName().matches(".*" + Pattern.quote(htmlOptionsModel.getHtmlPreExtensionOption()))) {
                            if (htmlOptionsModel.getGenerateNewHtmlFileOption()) {
                                outputFilePath += File.separator + file.getName() + htmlOptionsModel.getHtmlPreExtensionOption() + "." + file.getExt();
                            } else {
                                outputFilePath += File.separator + file.getNameExt();
                            }
                        }
                    }

                    MinifyFileResult minifyFileResult = compress(inputFilePath, "text/html", outputFilePath);
                    minifyResult.setInputHtmlFilesSize(minifyResult.getInputHtmlFilesSize() + minifyFileResult.getInputFileSize());
                    minifyResult.setOutputHtmlFilesSize(minifyResult.getOutputHtmlFilesSize() + minifyFileResult.getOutputFileSize());
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("xml") && folderOptionsModel.getFileExtensionsRegexOption().contains(file.getExt().toLowerCase())) {
                xmlFile++;
                try {
                    String inputFilePath = file.getPath();
                    String outputFilePath = targetFolder.getPath();
                    boolean minifyAllFiles = folderOptionsModel.getMinifyAllFilesOption();

                    if (minifyAllFiles) {
                        if (xmlOptionsModel.getGenerateNewXmlFileOption()) {
                            outputFilePath += File.separator + file.getName() + xmlOptionsModel.getXmlPreExtensionOption() + "." + file.getExt();
                        } else {
                            outputFilePath += File.separator + file.getNameExt();
                        }
                    } else {
                        if (!file.getName().matches(".*" + Pattern.quote(xmlOptionsModel.getXmlPreExtensionOption()))) {
                            if (xmlOptionsModel.getGenerateNewXmlFileOption()) {
                                outputFilePath += File.separator + file.getName() + xmlOptionsModel.getXmlPreExtensionOption() + "." + file.getExt();
                            } else {
                                outputFilePath += File.separator + file.getNameExt();
                            }
                        }
                    }

                    MinifyFileResult minifyFileResult = compress(inputFilePath, "text/xml-mime", outputFilePath);
                    minifyResult.setInputXmlFilesSize(minifyResult.getInputXmlFilesSize() + minifyFileResult.getInputFileSize());
                    minifyResult.setOutputXmlFilesSize(minifyResult.getOutputXmlFilesSize() + minifyFileResult.getOutputFileSize());
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            } else if (file.getExt().equalsIgnoreCase("json") && folderOptionsModel.getFileExtensionsRegexOption().contains(file.getExt().toLowerCase())) {
                jsonFile++;

                try {
                    String inputFilePath = file.getPath();
                    String outputFilePath = targetFolder.getPath();
                    boolean minifyAllFiles = folderOptionsModel.getMinifyAllFilesOption();

                    if (minifyAllFiles) {
                        if (jsonOptionsModel.getGenerateNewJsonFileOption()) {
                            outputFilePath += File.separator + file.getName() + jsonOptionsModel.getJsonPreExtensionOption() + "." + file.getExt();
                        } else {
                            outputFilePath += File.separator + file.getNameExt();
                        }
                    } else {
                        if (!file.getName().matches(".*" + Pattern.quote(jsonOptionsModel.getJsonPreExtensionOption()))) {
                            if (jsonOptionsModel.getGenerateNewJsonFileOption()) {
                                outputFilePath += File.separator + file.getName() + jsonOptionsModel.getJsonPreExtensionOption() + "." + file.getExt();
                            } else {
                                outputFilePath += File.separator + file.getNameExt();
                            }
                        }
                    }

                    MinifyFileResult minifyFileResult = compress(inputFilePath, "text/x-json", outputFilePath);
                    minifyResult.setInputJsonFilesSize(minifyResult.getInputJsonFilesSize() + minifyFileResult.getInputFileSize());
                    minifyResult.setOutputJsonFilesSize(minifyResult.getOutputJsonFilesSize() + minifyFileResult.getOutputFileSize());
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

//    public String compressSelectedJavaScript(String inputFilename, String content, MinifyOptions minifyProperty) throws IOException {
    public String compressSelectedJavaScript(String inputFilename, String content) throws IOException {
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

//    public void compressCssInternal(Reader in, Writer out, MinifyOptions minifyProperty) throws IOException {
    public void compressCssInternal(Reader in, Writer out) throws IOException {
        try {
            CssCompressor compressor = new CssCompressor(in);
            in.close();
            in = null;
//            compressor.compress(out, minifyProperty.getLineBreakPosition());
            compressor.compress(out, -1);
            out.flush();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

//    public void compressHtmlInternal(Reader in, Writer out, MinifyOptions minifyProperty) throws IOException {
    public void compressHtmlInternal(Reader in, Writer out) throws IOException {
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

//    public MinifyFileResult compressContent(String inputFilename, String content, String mimeType, String outputFilename, MinifyOptions minifyProperty) throws IOException {
    public MinifyFileResult compressContent(String inputFilename, String content, String mimeType, String outputFilename) throws IOException {
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
//            out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());
            out = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");

            String output;
            if (mimeType.contains("html")) {
                HtmlCompressor htmlCompressor = new HtmlCompressor();

                htmlCompressor.setRemoveIntertagSpaces(true);
//                    htmlCompressor.setCompressCss(minifyProperty.isBuildInternalCSSMinify());               //compress inline css
//                    htmlCompressor.setCompressJavaScript(minifyProperty.isBuildInternalJSMinify());        //compress inline javascript

                output = htmlCompressor.compress(content);

                if (StringUtils.isBlank(htmlOptionsModel.getHtmlHeaderOption())) {
                    out.write(output);
                } else {
                    out.write(htmlOptionsModel.getHtmlHeaderOption() + "\n" + output);
                }
            } else if (mimeType.contains("javascript")) {
                // TODO: Change to Google Closure Compiler.
                compiler.compile(CommandLineRunner.getDefaultExterns(), inputs, options);

                if (StringUtils.isBlank(jsOptionsModel.getJsHeaderOption())) {
                    out.write(compiler.toSource());
                } else {
                    out.write(jsOptionsModel.getJsHeaderOption() + "\n" + compiler.toSource());
                }
            } else if (mimeType.contains("css")) {
                // TODO: Change to CSSNano.
                Reader in = new StringReader(content);
                CssCompressor cssCompressor = new CssCompressor(in);
                in.close();
                StringWriter outputWriter = new StringWriter();
//                    cssCompressor.compress(outputWriter, minifyProperty.getLineBreakPosition());
                cssCompressor.compress(outputWriter, -1);
                outputWriter.flush();
                if (StringUtils.isBlank(cssOptionsModel.getCssHeaderOption())) {
                    out.write(outputWriter.toString());
                } else {
                    out.write(cssOptionsModel.getCssHeaderOption() + "\n" + outputWriter.toString());
                }
                outputWriter.close();
            } else if (mimeType.contains("json")) {
                JsonMinifyUtil jsonCompressor = new JsonMinifyUtil();
                output = jsonCompressor.minify(content);

                if (StringUtils.isBlank(jsonOptionsModel.getJsonHeaderOption())) {
                    out.write(output);
                } else {
                    out.write(jsonOptionsModel.getJsonHeaderOption() + "\n" + output);
                }
            } else if (mimeType.contains("xml")) {
                XmlCompressor xmlCompressor = new XmlCompressor();

                xmlCompressor.setRemoveIntertagSpaces(true);
                xmlCompressor.setRemoveComments(true);
                xmlCompressor.setEnabled(true);

                output = xmlCompressor.compress(content);

                if (StringUtils.isBlank(xmlOptionsModel.getXmlHeaderOption())) {
                    out.write(output);
                } else {
                    out.write(xmlOptionsModel.getXmlHeaderOption() + "\n" + output);
                }
            }

            out.flush();
        } finally {
            IOUtils.closeQuietly(out);
        }
        return minifyFileResult;
    }

//    public MinifyFileResult compress(String inputFilename, String mimeType, String outputFilename, MinifyOptions minifyProperty) throws IOException {
    public MinifyFileResult compress(String inputFilename, String mimeType, String outputFilename) throws IOException {
        System.out.println("Input: " + inputFilename);
        System.out.println("MimeType: " + mimeType);
        System.out.println("Output: " + outputFilename);

        InputStreamReader in = null;
        Writer out = null;
        MinifyFileResult minifyFileResult = new MinifyFileResult();
        Compiler compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();

        compiler.initOptions(options);

        options.setEmitUseStrict(false);
        options.setTrustedStrings(true);

        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        List<SourceFile> inputs = new ArrayList<>();
        inputs.add(SourceFile.fromFile(inputFilename));

        try {
            File inputFile = new File(inputFilename);
            File outputFile = new File(outputFilename);
//            in = new InputStreamReader(new FileInputStream(inputFile), minifyProperty.getCharset());
            in = new InputStreamReader(new FileInputStream(inputFile), "UTF-8");
            minifyFileResult.setInputFileSize(inputFile.length());
//            out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());
            out = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
            String output;

            if (mimeType.contains("html")) {
                HtmlCompressor htmlCompressor = new HtmlCompressor();
                htmlCompressor.setRemoveIntertagSpaces(true);
//                    htmlCompressor.setCompressCss(minifyProperty.isBuildInternalCSSMinify());               //compress inline css
//                    htmlCompressor.setCompressJavaScript(minifyProperty.isBuildInternalJSMinify());        //compress inline javascript

                output = htmlCompressor.compress(fromStream(in));
                out.write(htmlOptionsModel.getHtmlHeaderOption() + "\n" + output);
            } else if (mimeType.contains("javascript")) {
                // TODO: Change to Google Closure Compiler.
                compiler.compile(CommandLineRunner.getDefaultExterns(), inputs, options);

                if (StringUtils.isBlank(jsOptionsModel.getJsHeaderOption())) {
                    out.write(compiler.toSource());
                } else {
                    out.write(jsOptionsModel.getJsHeaderOption() + "\n" + compiler.toSource());
                }
            } else if (mimeType.contains("css")) {
                // TODO: Change to CSSNano.
                CssCompressor cssCompressor = new CssCompressor(in);
                StringWriter outputWriter = new StringWriter();

//                    cssCompressor.compress(outputWriter, minifyProperty.getLineBreakPosition());
                cssCompressor.compress(outputWriter, -1);
                outputWriter.flush();
                if (StringUtils.isBlank(jsOptionsModel.getJsHeaderOption())) {
                    out.write(outputWriter.toString());
                } else {
                    out.write(cssOptionsModel.getCssHeaderOption() + "\n" + outputWriter.toString());
                }
                outputWriter.close();
            } else if (mimeType.contains("json")) {
                JsonMinifyUtil jsonCompressor = new JsonMinifyUtil();

                output = jsonCompressor.minify(fromStream(in));
                out.write(jsonOptionsModel.getJsonHeaderOption() + "\n" + output);
            } else if (mimeType.contains("text/xml-mime")) {
                XmlCompressor xmlCompressor = new XmlCompressor();

                xmlCompressor.setRemoveIntertagSpaces(true);
                xmlCompressor.setRemoveComments(true);
                xmlCompressor.setEnabled(true);

                output = xmlCompressor.compress(fromStream(in));

                out.write(xmlOptionsModel.getXmlHeaderOption() + "\n" + output);
            }

            in.close();
            in = null;

            out.flush();

            minifyFileResult.setOutputFileSize(outputFile.length());
            if (folderOptionsModel.getAddLogToFileOption()) {
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

//    public void compressXmlInternal(Reader in, Writer out, MinifyOptions minifyProperty) throws IOException {
    public void compressXmlInternal(Reader in, Writer out) throws IOException {
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

//    public void compressJsonInternal(Reader in, Writer out, MinifyOptions minifyProperty) throws IOException {
    public void compressJsonInternal(Reader in, Writer out) throws IOException {
        try {
            JsonMinifyUtil compressor = new JsonMinifyUtil();
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
            extension = fileName.substring(extIndex + 1);
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
