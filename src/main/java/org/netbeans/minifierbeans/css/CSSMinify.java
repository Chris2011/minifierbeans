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
package org.netbeans.minifierbeans.css;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.JOptionPane;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.minifierbeans.ui.MinifyProperty;
import org.netbeans.minifierbeans.util.source.minify.MinifyFileResult;
import org.netbeans.minifierbeans.util.source.minify.MinifyUtil;
import org.openide.loaders.DataObject;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.awt.NotificationDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.TaskListener;
import org.openide.windows.TopComponent;

@ActionID(category = "Build",
        id = "org.netbeans.util.source.minify.CSSMinify")
@ActionRegistration(iconBase = "org/netbeans/minifierbeans/compress.png",
        displayName = "#CTL_CSSMinify")
@ActionReferences({
    @ActionReference(path = "Loaders/text/css/Actions", position = 300, separatorBefore = 250, separatorAfter = 350)
})
@Messages("CTL_CSSMinify=Minify CSS")
public final class CSSMinify implements ActionListener {

    private final DataObject context;
    private final static RequestProcessor RP = new RequestProcessor("CSSMinify", 1, true);

    public CSSMinify(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        execute(context, null, true);
    }

    public static void execute(final DataObject context, final String content, final boolean notify) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cssMinify(context, content, notify);
            }
        };

        final RequestProcessor.Task theTask = RP.create(runnable);
        final ProgressHandle ph = ProgressHandleFactory.createHandle("Minifying CSS " + context.getPrimaryFile().getName(), theTask);

        theTask.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(org.openide.util.Task task) {
                ph.finish();
            }
        });

        ph.start();
        theTask.schedule(0);
    }

//    private class ProcessLaunch implements Callable<Process> {
//
//        private final File folder;
//        private final String projectName;
//
//        public ProcessLaunch(File folder, String projectName) {
//            this.folder = folder;
//            this.projectName = projectName;
//        }
//
//        @Override
//        public Process call() throws Exception {
//            final String appPath = PostCssCliExecutable.POST_CSS_CLI_NAME;
//            ProcessBuilder pb = new ProcessBuilder(appPath, "new", projectName);
//
//            System.out.println(String.format("Execute \"%s\" new %s in \"%s\"", appPath, projectName, folder));
//
//            pb.directory(folder); //NOI18N
//            pb.redirectErrorStream(true);
////            pb.redirectOutput(ProcessBuilder.Redirect.)
//
//            return pb.start();
//        }
//    }
    private static void cssMinify(DataObject context, String content, boolean notify) {
        Project project = TopComponent.getRegistry().getActivated().getLookup().lookup(Project.class);
        FileObject file = context.getPrimaryFile();

        MinifyProperty minifyProperty = MinifyProperty.getInstance();
        MinifyUtil util = new MinifyUtil();
        MinifyFileResult minifyFileResult = new MinifyFileResult();
        Writer out = null;

        if (!util.isMinifiedFile(file.getName(), minifyProperty.getPreExtensionCSS())) {
            String inputFilePath = file.getPath();
            String outputFilePath;

            if (minifyProperty.isNewCSSFile() && minifyProperty.getPreExtensionCSS() != null && !minifyProperty.getPreExtensionCSS().trim().isEmpty()) {
                outputFilePath = file.getParent().getPath() + "/" + file.getName() + minifyProperty.getPreExtensionCSS() + "." + file.getExt();
            } else {
                outputFilePath = inputFilePath;
            }
            //                if (content != null) {
////                    minifyFileResult = util.compressContent(inputFilePath, content, "text/css", outputFilePath, minifyProperty);
//                } else {
////                    minifyFileResult = util.compress(inputFilePath, "text/css", outputFilePath, minifyProperty);
//                }
            File inputFile = new File(inputFilePath);
            File outputFile = new File(outputFilePath);
            minifyFileResult.setInputFileSize(inputFile.length());
//                out = new OutputStreamWriter(new FileOutputStream(outputFile), minifyProperty.getCharset());
//                CssCompressor compressor = new CssCompressor(in);
            StringWriter outputWriter = new StringWriter();
//                compressor.compress(outputWriter, minifyProperty.getLineBreakPosition());
            outputWriter.flush();
//                if (StringUtils.isBlank(MinifyProperty.getInstance().getHeaderCSS())) {
//                    out.write(outputWriter.toString());
//                } else {
//                    out.write(MinifyProperty.getInstance().getHeaderCSS() + "\n" + outputWriter.toString());
//                }
//                outputWriter.close();
            if (project == null) {
                project = FileOwnerQuery.getOwner(file);

                PostCssCliExecutable postCssCliExecutable = PostCssCliExecutable.getDefault(project);

                Future<Integer> task = postCssCliExecutable.generate(inputFile, outputFile);

                try {
                    task.get(1, TimeUnit.MINUTES);

                    minifyFileResult.setOutputFileSize(outputFile.length());
                    if (minifyProperty.isAppendLogToFile()) {
//                            out.append("\n<!--Size: " + minifyFileResult.getInputFileSize() + "=>"
//                                    + minifyFileResult.getOutputFileSize() + "Bytes "
//                                    + "\n Saved " + minifyFileResult.getSavedPercentage() + "%-->");
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException | TimeoutException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            if (minifyProperty.isEnableOutputLogAlert() && notify) {
                NotificationDisplayer.getDefault().notify("Successful CSS minification",
                        NotificationDisplayer.Priority.NORMAL.getIcon(), String.format(
                        "Input CSS Files Size: %s Bytes \n"
                        + "CSS Minified Completed Successfully\n"
                        + "After Minifying CSS Files Size: %s Bytes \n"
                        + "CSS Space Saved %s%%", minifyFileResult.getInputFileSize(), minifyFileResult.getOutputFileSize(), minifyFileResult.getSavedPercentage()), null);
            }
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

//        try {
//        } catch (HeadlessException | IOException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        return () -> {
        //            final ProgressHandle ph = ProgressHandle.createHandle("Creating project via angular-cli...");
        //
        //            try {
        //                ph.start();
        //
        //                File dirF = FileUtil.normalizeFile(parentLocation);
        //
        //                ExecutionDescriptor descriptor = new ExecutionDescriptor()
        //                        .controllable(true)
        //                        .frontWindow(true)
        //                        // disable rerun
        //                        .rerunCondition(new ExecutionDescriptor.RerunCondition() {
        //                            @Override
        //                            public void addChangeListener(ChangeListener cl) {
        //                            }
        //
        //                            @Override
        //                            public void removeChangeListener(ChangeListener cl) {
        //                            }
        //
        //                            @Override
        //                            public boolean isRerunPossible() {
        //                                return false;
        //                            }
        //                        })
        //                        // we handle the progress ourself
        //                        .showProgress(false);
        //
        //                // integrate as subtask in the same progress bar
        //                ph.progress(String.format("Executing 'ng new %s'", projectName));
        //
        //                ExecutionService exeService = ExecutionService.newService(new ProcessLaunch(parentLocation, projectName),
        //                        descriptor, String.format("Executing 'ng new %s'", projectName));
        //                Integer exitCode = null;
        //
        //                // this will run the process
        //                Future<Integer> processFuture = exeService.run();
        //
        //                try {
        //                    // wait for end of execution of shell command
        //                    exitCode = processFuture.get();
        //                } catch (InterruptedException | ExecutionException ex) {
        //                    NotificationDisplayer.getDefault().notify("Angular CLI execution was aborted", NotificationDisplayer.Priority.HIGH.getIcon(), String.format("The execution of 'ng new %s' was aborted. Please see the IDE Log.", projectName), null);
        //
        //                    return;
        //                } catch (CancellationException ex) {
        //                    NotificationDisplayer.getDefault().notify("Angular CLI execution was canceled", NotificationDisplayer.Priority.HIGH.getIcon(), String.format("The execution of 'ng new %s' was canceled by the user.", projectName), null);
        //
        //                    return;
        //                }
        //
        //                if (exitCode != null && exitCode != 0) {
        //                    NotificationDisplayer.getDefault().notify("Angular CLI execution was aborted", NotificationDisplayer.Priority.HIGH.getIcon(), String.format("The execution of 'ng new %s' was aborted. Please see the IDE Log.", projectName), null);
        //
        //                    return;
        //                }
        //
        //                if (exitCode != null && exitCode == 0) {
        //                    NotificationDisplayer.getDefault().notify(String.format("Project %s was successfully created", projectName), NotificationDisplayer.Priority.HIGH.getIcon(), String.format("The execution of 'ng new %s' was canceled by the user.", projectName), null);
        //
        //                    ph.progress("Opening project");
        //
        //                    FileObject dir = FileUtil.toFileObject(projectDir);
        //                    dir.refresh();
        //                    // TODO show error and abort if generation failed (f.e. missing package.json whatever)
        //
        //                    Project p = FileOwnerQuery.getOwner(dir);
        //
        //                    if (null != p) {
        //                        OpenProjects.getDefault().open(new Project[]{p}, true, true);
        //                    } else {
        //                        // TODO show error and abort if no project found (can happen when JS plugins are disabled)
        //                    }
        //                }
        //            } finally {
        //                ph.finish();
        //            }
        //        };
    }
}