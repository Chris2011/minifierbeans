package org.netbeans.minifierbeans.css;

import org.netbeans.minifierbeans.util.StringUtils;
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
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.minifierbeans.ExternalExecutable;
import org.netbeans.minifierbeans.validators.ValidationResult;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

public class CssNanoCliExecutable {
    public static final String POST_CSS_CLI_NAME = "\\src\\main\\resources\\org\\netbeans\\minifierbeans\\packages\\node_modules\\postcss-cli\\bin\\postcss";

    protected final Project project;
    protected final String cssNanoCliPath;

    private static final String OPTIONS_PATH = "HTML5/Minifier";

    CssNanoCliExecutable(String cssNanoCliPath, @NullAllowed Project project) {
        assert cssNanoCliPath != null;

        this.cssNanoCliPath = cssNanoCliPath;
        this.project = project;
    }

    @CheckForNull
    public static CssNanoCliExecutable getDefault(@NullAllowed Project project, boolean showOptions) {
        return createExecutable(POST_CSS_CLI_NAME, project);
    }

    private static CssNanoCliExecutable createExecutable(String cssNanoCli, Project project) {
        if (Utilities.isMac()) {
            return new CssNanoCliExecutable.MacCssNanoCliExecutable(cssNanoCli, project);
        }
        return new CssNanoCliExecutable(cssNanoCli, project);
    }

    String getCommand() {
        return cssNanoCliPath;
    }

    @NbBundle.Messages({
        "# {0} - project name",
        "CssNanoCliExecutable.generate=CSSNano CLI ({0})",})
    public Future<Integer> generate(FileObject target, boolean less) {
        assert !EventQueue.isDispatchThread();
        assert project != null;

        String projectName = ProjectUtils.getInformation(project).getDisplayName();
        Future<Integer> task = getExecutable("Bundle.CssNanoCliExecutable_generate(projectName)")
                .additionalParameters(getGenerateParams(target, less))
                .run(getDescriptor());

        assert task != null : cssNanoCliPath;
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

    private List<String> getGenerateParams(FileObject target, boolean less) {
        List<String> params = new ArrayList<String>(3);
//        params.add(FORCE_PARAM);
        params.add(FileUtil.toFile(target).getAbsolutePath());
        return getParams(params);
    }

    List<String> getParams(List<String> params) {
        assert params != null;
        return params;
    }

    @CheckForNull
    private static String validateResult(ValidationResult result) {
        if (result.isFaultless()) {
            return null;
        }
        if (result.hasErrors()) {
            return result.getFirstErrorMessage();
        }
        return result.getFirstWarningMessage();
    }

    //~ Inner classes
    private static final class MacCssNanoCliExecutable extends CssNanoCliExecutable {

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
            sb.append(cssNanoCliPath);
            sb.append("\" \""); // NOI18N
            sb.append(StringUtils.implode(super.getParams(params), "\" \"")); // NOI18N
            sb.append("\""); // NOI18N
            return Collections.singletonList(sb.toString());
        }

    }

}
