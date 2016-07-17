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
package org.netbeans.minify.ui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.openide.util.Exceptions;


public class MinifyPropertyController {
    
    
    
     void writeMinifyProperty(MinifyProperty minifyProperty) {
        File file ;
        OutputStream fileOut = null;
        ObjectOutput output ;
        try {
            file = new File("MinifyProperty.nb");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOut = new FileOutputStream(file);
            OutputStream buffer = new BufferedOutputStream(fileOut);
            output = new ObjectOutputStream(buffer);
            output.writeObject(minifyProperty);
            output.close();
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    MinifyProperty readMinifyProperty() {
        ObjectInput input = null;
        MinifyProperty minifyProperty = null;
        try {
            InputStream file = new FileInputStream("MinifyProperty.nb");
            InputStream buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);
            minifyProperty = (MinifyProperty) input.readObject();
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            minifyProperty = MinifyProperty.getInstance();
//            Exceptions.printStackTrace(ex);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return minifyProperty;
    }
    
}
