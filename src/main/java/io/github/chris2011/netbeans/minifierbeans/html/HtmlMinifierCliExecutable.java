package io.github.chris2011.netbeans.minifierbeans.html;

import io.github.chris2011.netbeans.minifierbeans.util.StringUtils;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.project.Project;
import io.github.chris2011.netbeans.minifierbeans.ExternalExecutable;
import io.github.chris2011.netbeans.minifierbeans.util.FileUtils;
import java.util.Arrays;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

public class HtmlMinifierCliExecutable {

    public static final String HTML_MINIFIER_CLI_NAME;

    private static final String OUTPUT_FILE_PARAM = "--output"; // NOI18N
    private static final String CONFIG_DIR = System.getProperty("user.home") + "/.netbeans/minifierbeans/custom-packages";

    protected final Project project;
    protected final String htmlMinifierPath;

    static {
        if (Utilities.isWindows()) {
            HTML_MINIFIER_CLI_NAME = CONFIG_DIR + "/html-minifier-terser.cmd"; // NOI18N
        } else {
            HTML_MINIFIER_CLI_NAME = CONFIG_DIR + "/html-minifier-terser"; // NOI18N
        }
    }

    private static final String OPTIONS_PATH = "HTML5/Minifier";

    HtmlMinifierCliExecutable(String htmlMinifierCliPath, @NullAllowed Project project) {
        assert htmlMinifierCliPath != null;

        this.htmlMinifierPath = htmlMinifierCliPath;
        this.project = project;
    }

    @CheckForNull
    public static HtmlMinifierCliExecutable getDefault(@NullAllowed Project project) {
        return createExecutable(HTML_MINIFIER_CLI_NAME, project);
    }

    private static HtmlMinifierCliExecutable createExecutable(String htmlMinifierCli, Project project) {
        if (Utilities.isMac()) {
            return new HtmlMinifierCliExecutable.MacHtmlMinifierCliExecutable(htmlMinifierCli, project);
        }
        return new HtmlMinifierCliExecutable(htmlMinifierCli, project);
    }

    String getCommand() {
        return htmlMinifierPath;
    }

    @NbBundle.Messages({
        "# {0} - project name",
        "HtmlMinifierCliExecutable.generate=HTML Minifier CLI ({0})",})
    public Future<Integer> generate(File inputFile, File outputFile, String compilerFlags) {
        assert !EventQueue.isDispatchThread();
        assert project != null;

        Future<Integer> task = getExecutable("Minification in progress")
                .additionalParameters(getGenerateParams(inputFile, outputFile, compilerFlags))
                .run(getDescriptor());

        assert task != null : htmlMinifierPath;
        return task;
    }

    private ExternalExecutable getExecutable(String title) {
        assert title != null;

        return new ExternalExecutable(getCommand())
                .workDir(getWorkDir())
                .displayName(title)
                .optionsPath(OPTIONS_PATH)
                .noOutput(false);
    }

    private ExecutionDescriptor getDescriptor() {
        assert project != null;

        return ExternalExecutable.DEFAULT_EXECUTION_DESCRIPTOR
                .showSuspended(true)
                .optionsPath(OPTIONS_PATH)
                .outLineBased(true)
                .errLineBased(true)
                .postExecution(new Runnable() {
                    @Override
                    public void run() {
                        project.getProjectDirectory().refresh();
                    }
                });
    }

    private File getWorkDir() {
        if (project == null) {
            return FileUtils.TMP_DIR;
        }

        File workDir = FileUtil.toFile(project.getProjectDirectory());

        assert workDir != null : project.getProjectDirectory();

        return workDir;
    }

    private List<String> getGenerateParams(File inputFile, File outputFile, String compilerFlags) {
        List<String> params = new ArrayList<>();

        params.add(inputFile.getAbsolutePath().replace("\\", "/"));

        if (!compilerFlags.isEmpty()) {
            String[] splittedCompilerFlags = compilerFlags.split("; ");

            for (String splittedCompilerFlag : splittedCompilerFlags) {
                String[] splittedKeyAndValue = splittedCompilerFlag.split(" ");

                params.addAll(Arrays.asList(splittedKeyAndValue));
            }
        }

        params.add(OUTPUT_FILE_PARAM);
        params.add(outputFile.getAbsolutePath().replace("\\", "/"));

        return getParams(params);
    }

    List<String> getParams(List<String> params) {
        assert params != null;

        return params;
    }

    //~ Inner classes
    private static final class MacHtmlMinifierCliExecutable extends HtmlMinifierCliExecutable {

        private static final String BASH_COMMAND = "/bin/bash -lc"; // NOI18N

        MacHtmlMinifierCliExecutable(String htmlMinifierCliPath, Project project) {
            super(htmlMinifierCliPath, project);
        }

        @Override
        String getCommand() {
            return BASH_COMMAND;
        }

        @Override
        List<String> getParams(List<String> params) {
            StringBuilder sb = new StringBuilder(200);
            sb.append("\""); // NOI18N
            sb.append(htmlMinifierPath);
            sb.append("\" \""); // NOI18N
            sb.append(StringUtils.implode(super.getParams(params), "\" \"")); // NOI18N
            sb.append("\""); // NOI18N

            return Collections.singletonList(sb.toString());
        }

    }

}
