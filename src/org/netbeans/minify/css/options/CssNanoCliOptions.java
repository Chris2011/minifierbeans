package org.netbeans.minify.css.options;

import org.netbeans.minify.css.FileUtils;
import java.util.List;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.minify.css.CssNanoCliExecutable;
import org.openide.util.NbPreferences;

public class CssNanoCliOptions {
    public static final String CSS_NANO_CLI_PATH = "css-nano-cli.path"; // NOI18N

    // Do not change arbitrary - consult with layer's folder OptionsExport
    // Path to Preferences node for storing these preferences
    private static final String PREFERENCES_PATH = "nodejs"; // NOI18N

    private static final CssNanoCliOptions INSTANCE = new CssNanoCliOptions();

    private final Preferences preferences;
    private volatile boolean cssNanoCliSearched = false;

    public static CssNanoCliOptions getInstance() {
        return INSTANCE;
    }

    public void addPreferenceChangeListener(PreferenceChangeListener listener) {
        preferences.addPreferenceChangeListener(listener);
    }

    public void removePreferenceChangeListener(PreferenceChangeListener listener) {
        preferences.removePreferenceChangeListener(listener);
    }

    private CssNanoCliOptions() {
        preferences = NbPreferences.forModule(CssNanoCliOptions.class).node(PREFERENCES_PATH);
    }

    @CheckForNull
    public String getCssNanoCli() {
        String path = preferences.get(CSS_NANO_CLI_PATH, null);

        if (path == null && !cssNanoCliSearched) {
            cssNanoCliSearched = true;
            List<String> files = FileUtils.findFileOnUsersPath(CssNanoCliExecutable.CSS_NANO_CLI_NAME);

            if (!files.isEmpty()) {
                path = files.get(0);
                setCssNanoCli(path);
            }
        }

        return path;
    }

    public void setCssNanoCli(String cssNanoCli) {
        preferences.put(CSS_NANO_CLI_PATH, cssNanoCli);
    }
}
