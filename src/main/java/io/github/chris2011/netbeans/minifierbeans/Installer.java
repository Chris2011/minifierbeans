package io.github.chris2011.netbeans.minifierbeans;

import com.vdurmont.semver4j.Semver;
import io.github.chris2011.netbeans.minifierbeans.util.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;
import org.openide.windows.OnShowing;

@OnShowing
public class Installer extends ModuleInstall implements Runnable {
    private static final RequestProcessor RP = new RequestProcessor(Installer.class);

    @Override
    public void run() {
        RP.post(() -> {
            try {
                Path customPackagesFolder = Paths.get(System.getProperty("user.home") + "/.netbeans/minifierbeans/custom-packages");
                
                if (Files.exists(customPackagesFolder)) {
                    String remoteVersion = getVersionFromRemotePackageJson();
                    String localVersion = getVersionFromLocalPackageJson();
                    
                    if (localVersion.equals("") && FileUtils.deleteDirectory(customPackagesFolder.toFile())) {
                        FileUtils.downloadFile(System.getProperty("user.home"));
                        
                        return;
                    }
                    
                    Semver remoteSemVersion = new Semver(remoteVersion);
                    Semver localSemVersion = new Semver(localVersion);
                    
                    if (remoteSemVersion.isGreaterThan(localSemVersion) && FileUtils.deleteDirectory(customPackagesFolder.toFile())) {
                        FileUtils.downloadFile(System.getProperty("user.home"));
                    }
                } else {
                    FileUtils.downloadFile(System.getProperty("user.home"));
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }, 0);
    }

    private String getVersionFromRemotePackageJson() {
        String version = "";

        try {
            String sURL = "https://raw.githubusercontent.com/Chris2011/minifierbeans/master/custom-packages/package.json";

            // Convert to a JSON object to print data
            JSONObject json = new JSONObject(IOUtils.toString(new URL(sURL), Charset.forName("UTF-8")));

            version = json.getString("version");
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return version.isEmpty() ? "" : version;
    }

    private String getVersionFromLocalPackageJson() throws IOException {
        String version = "";

        try {
            String packageJsonPath = Paths.get(System.getProperty("user.home") + "/.netbeans/minifierbeans/custom-packages/package.json").toString();

            File f = new File(packageJsonPath);

            if (f.exists()) {
                InputStream is = new FileInputStream(packageJsonPath);

                JSONObject json = new JSONObject(IOUtils.toString(is, "UTF-8"));

                if (json.has("version")) {
                    version = json.getString("version");
                }
            }
        } catch (JSONException ex) {
            FileUtils.downloadFile(System.getProperty("user.home"));
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }

        return version.isEmpty() ? "" : version;
    }
}
