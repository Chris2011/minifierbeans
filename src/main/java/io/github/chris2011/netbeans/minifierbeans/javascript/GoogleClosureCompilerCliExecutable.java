package io.github.chris2011.netbeans.minifierbeans.javascript;

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
import org.apache.commons.lang.StringUtils;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

public class GoogleClosureCompilerCliExecutable {

    public static final String GOOGLE_CLOSURE_COMPILER_CLI_NAME;

    private static final String INPUT_FILE_PARAM = "--js"; // NOI18N
    private static final String OUTPUT_FILE_PARAM = "--js_output_file"; // NOI18N
    private static final String CONFIG_DIR = System.getProperty("user.home") + "/.netbeans/minifierbeans/custom-packages";

    protected final Project project;
    protected final String googleClosureCompilerPath;

    static {
        if (Utilities.isWindows()) {
            GOOGLE_CLOSURE_COMPILER_CLI_NAME = CONFIG_DIR + "/google-closure-compiler.cmd"; // NOI18N
        } else {
            GOOGLE_CLOSURE_COMPILER_CLI_NAME = CONFIG_DIR + "/google-closure-compiler"; // NOI18N
        }
    }

    private static final String OPTIONS_PATH = "HTML5/Minifier";

    GoogleClosureCompilerCliExecutable(String googleClosureCompilerCliPath, @NullAllowed Project project) {
        assert googleClosureCompilerCliPath != null;

        this.googleClosureCompilerPath = googleClosureCompilerCliPath;
        this.project = project;
    }

    @CheckForNull
    public static GoogleClosureCompilerCliExecutable getDefault(@NullAllowed Project project) {
        return createExecutable(GOOGLE_CLOSURE_COMPILER_CLI_NAME, project);
    }

    private static GoogleClosureCompilerCliExecutable createExecutable(String googleClosureCompilerCli, Project project) {
        if (Utilities.isMac()) {
            return new GoogleClosureCompilerCliExecutable.MacGoogleClosureCompilerCliExecutable(googleClosureCompilerCli, project);
        }
        return new GoogleClosureCompilerCliExecutable(googleClosureCompilerCli, project);
    }

    String getCommand() {
        return googleClosureCompilerPath;
    }

    @NbBundle.Messages({
        "# {0} - project name",
        "GoogleClosureCompilerCliExecutable.generate=Google Closure Compiler CLI ({0})",})
    public Future<Integer> generate(File inputFile, File outputFile, String compilerFlags) {
        assert !EventQueue.isDispatchThread();
        assert project != null;

        Future<Integer> task = getExecutable("Minification in progress")
                .additionalParameters(getGenerateParams(inputFile, outputFile, compilerFlags))
                .run(getDescriptor());

        try {
            assert task != null : googleClosureCompilerPath;
        } catch (AssertionError e) {
            e.printStackTrace();
        }
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

        if (!compilerFlags.isEmpty()) {
            String[] splittedCompilerFlags = compilerFlags.split("; ");

            for (String splittedCompilerFlag : splittedCompilerFlags) {
                String[] splittedKeyAndValue = splittedCompilerFlag.split(" ");

                params.addAll(Arrays.asList(splittedKeyAndValue));
            }
        }

        params.add(INPUT_FILE_PARAM);
        params.add(inputFile.getAbsolutePath().replace("\\", "/"));
        params.add(OUTPUT_FILE_PARAM);
        params.add(outputFile.getAbsolutePath().replace("\\", "/"));

        return getParams(params);
    }

    List<String> getParams(List<String> params) {
        assert params != null;

        return params;
    }

    //~ Inner classes
    private static final class MacGoogleClosureCompilerCliExecutable extends GoogleClosureCompilerCliExecutable {

        private static final String BASH_COMMAND = "/bin/bash -lc"; // NOI18N

        MacGoogleClosureCompilerCliExecutable(String googleClosureCompileStringCliPath, Project project) {
            super(googleClosureCompileStringCliPath, project);
        }

        @Override
        String getCommand() {
            return BASH_COMMAND;
        }

        @Override
        List<String> getParams(List<String> params) {
            StringBuilder sb = new StringBuilder(200);
            sb.append("\""); // NOI18N
            sb.append(googleClosureCompilerPath);
            sb.append("\" \""); // NOI18N
            sb.append(StringUtils.join(super.getParams(params), "\" \"")); // NOI18N
            sb.append("\""); // NOI18N

            return Collections.singletonList(sb.toString());
        }

    }

}
