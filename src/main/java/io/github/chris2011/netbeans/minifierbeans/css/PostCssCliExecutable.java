package io.github.chris2011.netbeans.minifierbeans.css;

import io.github.chris2011.netbeans.minifierbeans.util.FileUtils;
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
import org.apache.commons.lang.StringUtils;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

public class PostCssCliExecutable {
    public static final String POST_CSS_CLI_NAME;

    private static final String OUTPUT_FILE_PARAM = "-o"; // NOI18N
    private static final String CONFIG_DIR_PARAM = "--config"; // NOI18N
    private static final String CONFIG_DIR = System.getProperty("user.home") + "/.netbeans/minifierbeans/custom-packages";

    protected final Project project;
    protected final String postCssPath;

    static {
        if (Utilities.isWindows()) {
            POST_CSS_CLI_NAME = CONFIG_DIR + "/postcss.cmd"; // NOI18N
        } else {
            POST_CSS_CLI_NAME = CONFIG_DIR + "/postcss"; // NOI18N
        }
    }

    private static final String OPTIONS_PATH = "HTML5/Minifier";

    PostCssCliExecutable(String postCssCliPath, @NullAllowed Project project) {
        assert postCssCliPath != null;

        this.postCssPath = postCssCliPath;
        this.project = project;
    }

    @CheckForNull
    public static PostCssCliExecutable getDefault(@NullAllowed Project project) {
        return createExecutable(POST_CSS_CLI_NAME, project);
    }

    private static PostCssCliExecutable createExecutable(String cssNanoCli, Project project) {
        if (Utilities.isMac()) {
            return new PostCssCliExecutable.MacCssNanoCliExecutable(cssNanoCli, project);
        }
        return new PostCssCliExecutable(cssNanoCli, project);
    }

    String getCommand() {
        return postCssPath;
    }

    @NbBundle.Messages({
        "# {0} - project name",
        "CssNanoCliExecutable.generate=CSSNano CLI ({0})",})
    public Future<Integer> generate(File inputFile, File outputFile) {
        assert !EventQueue.isDispatchThread();
        assert project != null;

//        String projectName = ProjectUtils.getInformation(project).getDisplayName();
//        Future<Integer> task = getExecutable(Bundle.CssNanoCliExecutable_generate(projectName))
        Future<Integer> task = getExecutable("Minification in progress")
                .additionalParameters(getGenerateParams(inputFile, outputFile))
                .run(getDescriptor());
//        ProcessBuilder pb = getExecutable("Minification in progress");
//        pb.setArguments(getGenerateParams(inputFile, outputFile));
//        Future<Integer> task = 
////                .setArguments().call();
////                .run(getDescriptor());

        try {
            assert task != null : postCssPath;
        } catch (AssertionError e) {
            e.printStackTrace();
        }
        return task;
    }

    private ExternalExecutable getExecutable(String title) {
        assert title != null;

//        ProcessBuilder pb = ProcessBuilder.getLocal();
//        pb.setWorkingDirectory(getWorkDir());
//
//        return pb;
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
//            return FileUtils.TMP_DIR.getAbsolutePath();
            return FileUtils.TMP_DIR;
        }

        File workDir = FileUtil.toFile(project.getProjectDirectory());

        assert workDir != null : project.getProjectDirectory();

//        return workDir.getAbsolutePath();
        return workDir;
    }

    private List<String> getGenerateParams(File inputFile, File outputFile) {
        List<String> params = new ArrayList<>(3);

        params.add(CONFIG_DIR_PARAM);
        params.add(CONFIG_DIR);
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
    private static final class MacCssNanoCliExecutable extends PostCssCliExecutable {

        private static final String BASH_COMMAND = "/bin/bash -lc"; // NOI18N

        MacCssNanoCliExecutable(String cssNanoCliPath, Project project) {
            super(cssNanoCliPath, project);
        }

        @Override
        String getCommand() {
            return BASH_COMMAND;
        }

        @Override
        List<String> getParams(List<String> params) {
            StringBuilder sb = new StringBuilder(200);
            sb.append("\""); // NOI18N
            sb.append(postCssPath);
            sb.append("\" \""); // NOI18N
            sb.append(StringUtils.join(super.getParams(params), "\" \"")); // NOI18N
            sb.append("\""); // NOI18N
            return Collections.singletonList(sb.toString());
        }

    }

}
