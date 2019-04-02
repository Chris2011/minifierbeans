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
package org.netbeans.util.image.compress;

import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JOptionPane;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import java.util.Base64;

/**
 *
 * @author SERVO006
 */
public class ImageUtil {

    public BufferedImage decodeToImage(String imageString, String filePath, String fileType) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            imageByte = decoder.decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);

            ImageIO.write(image, fileType, new File(filePath));

            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public String encodeToString(String filePath, String type) {
        String imageString = null;
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            Base64.Encoder encoder = Base64.getEncoder();
            imageString = encoder.encodeToString(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public void compress(String inputFilePath, String outputFilePath, String fileType) {
        if (fileType.equalsIgnoreCase("JPEG") || fileType.equalsIgnoreCase("JPG")) {
            compressJPG(inputFilePath, outputFilePath, fileType);
        } else if (fileType.equalsIgnoreCase("PNG")) {
            compressPNG(inputFilePath, outputFilePath, fileType);
        } else {
            // TODO: Adding notification to which image types are supported or change the behaviour.
            JOptionPane.showMessageDialog(null, "Currently only JPG/PNG File Compression is supported", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void compressPNG(String inputFilePath, String outputFilePath, String fileType) {
        try {
            PngOptimizer optimizer = new PngOptimizer();
            PngImage image = new PngImage(inputFilePath);
            optimizer.optimize(image, outputFilePath, false, 9);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compressJPG(String inputFilePath, String outputFilePath, String fileType) {
        InputOutput io = IOProvider.getDefault().getIO(Bundle.CTL_Base64Encode(), false);
        try {
            File imageFile = new File(inputFilePath);
            File compressedImageFile = new File(outputFilePath);

            InputStream is = new FileInputStream(imageFile);
            OutputStream os = new FileOutputStream(compressedImageFile);
            float quality = 0.5f;
            // create a BufferedImage as the result of decoding the supplied InputStream
            BufferedImage image = ImageIO.read(is);
            // get all image writers for JPG format
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileType);

            if (!writers.hasNext()) {
                throw new IllegalStateException("No writers found");
            }
            ImageWriter writer = writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            // appends a complete image stream containing a single image and
            //associated stream and image metadata and thumbnails to the output
            writer.write(null, new IIOImage(image, null, null), param);
            is.close();
            os.close();
            ios.close();
            writer.dispose();
        } catch (IOException ex) {
            io.getOut().println("Exception: " + ex.toString());
        }
    }
}