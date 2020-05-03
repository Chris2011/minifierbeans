package org.netbeans.minifierbeans;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;
import org.openide.windows.OnShowing;

@OnShowing
public class Installer extends ModuleInstall implements Runnable {
    private final int BUFFER_SIZE = 4096;
    private final String DOWNLOAD_URL = "https://github.com/Chris2011/minifierbeans/releases/download/1.0.0-cp/custom-packages.zip";
    private static final RequestProcessor RP = new RequestProcessor(Installer.class);

    @Override
    public void run() {
        // later
        RP.post(new Runnable() {
            @Override
            public void run() {
                try {
                    downloadFile(DOWNLOAD_URL, System.getProperty("user.home"));
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }, 0);
    }

    /**
     * Downloads a file from a URL
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    private void downloadFile(String fileURL, String saveDir)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "custom-packages.zip";

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();

            final File theFile = new File(saveDir + "/.netbeans/minifierbeans");
            theFile.mkdirs();

            final String saveFilePath = saveDir + "/.netbeans/minifierbeans/" + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            RP.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        extractArchive(saveFilePath, theFile.getAbsolutePath() + "/");
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            });
        } else {
            // TODO: Confert to notification.
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }

        httpConn.disconnect();
    }

    private void extractArchive(String fileZip, String destDir) throws FileNotFoundException, IOException {
        //Open the file 
        try (ZipFile file = new ZipFile(fileZip)) {
            FileSystem fileSystem = FileSystems.getDefault();
            //Get file entries
            Enumeration<? extends ZipEntry> entries = file.entries();

            //We will unzip files in this folder
            //Iterate over entries
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                //If directory then create a new directory in uncompressed folder
                if (entry.isDirectory()) {
                    System.out.println("Creating Directory:" + destDir + entry.getName());
                    Files.createDirectories(fileSystem.getPath(destDir + entry.getName()));
                } //Else create the file
                else {
                    InputStream is = file.getInputStream(entry);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String uncompressedFileName = destDir + entry.getName();
                    Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                    Files.createFile(uncompressedFilePath);
                    FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);

                    while (bis.available() > 0) {
                        fileOutput.write(bis.read());
                    }

                    fileOutput.close();
                    System.out.println("Written :" + entry.getName());
                }
            }

            JOptionPane.showMessageDialog(null, "Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
