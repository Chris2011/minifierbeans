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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public final class JSCSSMinifyCompressCustomPanel extends JSCSSMinifyCompressPanel {

    private MinifyProperty minifyProperty;
    private MinifyPropertyController minifyPropertyController = null;
    JSCSSMinifyCompressCustomPanel(JSCSSMinifyCompressOptionsPanelController controller) {
        super(controller);

        minifyProperty = MinifyProperty.getInstance();
minifyPropertyController = new MinifyPropertyController();

        if (minifyProperty.isNewJSFile()) {
            newJSFile.setSelected(true);
            preExtensionJS.setEnabled(Boolean.TRUE);
            preExtensionJS_Label.setEnabled(Boolean.TRUE);
            skipPreExtensionJS.setEnabled(true);
            if (minifyProperty.getPreExtensionJS() != null) {
                this.preExtensionJS.setText(minifyProperty.getPreExtensionJS());
            }
        } else {
            newJSFile.setSelected(false);
            preExtensionJS.setEnabled(Boolean.FALSE);
            preExtensionJS_Label.setEnabled(Boolean.FALSE);
            skipPreExtensionJS.setEnabled(false);
        }

       if (minifyProperty.isJsObfuscate()) {
            this.jsObfuscate.setSelected(true);
        }
//        if (minifyProperty.isPreserveSemicolon()) {
//            this.preserveSemicolon.setSelected(true);
//        }

        this.newJSFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewJSFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionJS("min");
                    preExtensionJS.setText("min");
                    preExtensionJS.setEnabled(Boolean.TRUE);
                    preExtensionJS_Label.setEnabled(Boolean.TRUE);
                    if(minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()){
                    skipPreExtensionJS.setEnabled(true);
                     minifyProperty.setSkipPreExtensionJS(Boolean.TRUE);
                        skipPreExtensionJS.setSelected(true);
                    }
                } else {
                    minifyProperty.setNewJSFile(Boolean.FALSE);
                    minifyProperty.setPreExtensionJS(null);
                    preExtensionJS.setText("");
                    preExtensionJS.setEnabled(Boolean.FALSE);
                    preExtensionJS_Label.setEnabled(Boolean.FALSE);
                    skipPreExtensionJS.setEnabled(false);
                    minifyProperty.setSkipPreExtensionJS(Boolean.FALSE);
                    skipPreExtensionJS.setSelected(false);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
        
     
        
        
        
//        this.preExtensionJS.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                minifyProperty.setPreExtensionJS(preExtensionJS.getText());
//                minifyPropertyController.writeMinifyProperty(minifyProperty);
//            }
//        });
        
          this.preExtensionJS.addFocusListener(new FocusListener(){
        @Override
        public void focusGained(FocusEvent fe){   }

        @Override
        public void focusLost(FocusEvent fe) {
            String text = preExtensionJS.getText();
            if(text==null || text.trim().isEmpty()){
               text = "min";
               preExtensionJS.setText(text);
           } 
           minifyProperty.setPreExtensionJS(text);
           minifyPropertyController.writeMinifyProperty(minifyProperty);
        }
    });
        
        
        
        

        this.jsObfuscate.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setJsObfuscate(Boolean.TRUE);
                } else {
                    minifyProperty.setJsObfuscate(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
//        this.preserveSemicolon.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    minifyProperty.setPreserveSemicolon(Boolean.TRUE);
//                } else {
//                    minifyProperty.setPreserveSemicolon(Boolean.FALSE);
//                }
//                minifyPropertyController.writeMinifyProperty(minifyProperty);
//            }
//        });




        if (minifyProperty.isNewCSSFile()) {
            newCSSFile.setSelected(true);
            preExtensionCSS.setEnabled(Boolean.TRUE);
            preExtensionCSS_Label.setEnabled(true);
            skipPreExtensionCSS.setEnabled(true);
            if (minifyProperty.getPreExtensionCSS() != null) {
                this.preExtensionCSS.setText(minifyProperty.getPreExtensionCSS());
            }
         } else {
            newCSSFile.setSelected(false);
            preExtensionCSS.setEnabled(Boolean.FALSE);
            preExtensionCSS_Label.setEnabled(false);
            skipPreExtensionCSS.setEnabled(false);
        }
        
      




        this.newCSSFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewCSSFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionCSS("min");
                    preExtensionCSS.setText("min");
                    preExtensionCSS.setEnabled(Boolean.TRUE);
                    preExtensionCSS_Label.setEnabled(true);
                     if(minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()){
                        skipPreExtensionCSS.setEnabled(true);
                        minifyProperty.setSkipPreExtensionCSS(Boolean.TRUE);
                        skipPreExtensionCSS.setSelected(true);
                    }
                    
                } else {
                    minifyProperty.setNewCSSFile(Boolean.FALSE);
                    minifyProperty.setPreExtensionCSS(null);
                    preExtensionCSS.setText("");
                    preExtensionCSS.setEnabled(Boolean.FALSE);
                    preExtensionCSS_Label.setEnabled(false);
                    skipPreExtensionCSS.setEnabled(false);
                       minifyProperty.setSkipPreExtensionCSS(Boolean.FALSE);
                    skipPreExtensionCSS.setSelected(false);
                
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });


//        this.preExtensionCSS.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(KeyEvent e) {
//                minifyProperty.setPreExtensionCSS(preExtensionCSS.getText());
//                minifyPropertyController.writeMinifyProperty(minifyProperty);
//            }
//        });


       this.preExtensionCSS.addFocusListener(new FocusListener(){
        @Override
        public void focusGained(FocusEvent fe){   }

        @Override
        public void focusLost(FocusEvent fe) {
            String text = preExtensionCSS.getText();
            if(text==null || text.trim().isEmpty()){
               text = "min";
               preExtensionCSS.setText(text);
           } 
           minifyProperty.setPreExtensionCSS(text);
           minifyPropertyController.writeMinifyProperty(minifyProperty);
        }
    });




        if (minifyProperty.isSeparatBuild()) {
            this.separatBuild.setSelected(true);
        }

        if (minifyProperty.isBuildCSSMinify()) {
            this.buildCSSMinify.setSelected(true);
           }

        if (minifyProperty.isBuildJSMinify()) {
            this.buildJSMinify.setSelected(true);
        } 
        
        if (minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
            skipPreExtensionCSS.setEnabled(true);
            if (minifyProperty.isSkipPreExtensionCSS()) {
                skipPreExtensionCSS.setSelected(true);
            } else {
                skipPreExtensionCSS.setSelected(false);
            }
        } else {
            this.skipPreExtensionCSS.setEnabled(false);
            skipPreExtensionCSS.setSelected(false);
        }

        if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
            this.skipPreExtensionJS.setEnabled(true);
            if (minifyProperty.isSkipPreExtensionJS()) {
                skipPreExtensionJS.setSelected(true);
            } else {
                skipPreExtensionJS.setSelected(false);
            }
        } else {
            this.skipPreExtensionJS.setEnabled(false);
            skipPreExtensionJS.setSelected(false);
        }
        
        
        
        
        
        
        
        
        
        

        this.separatBuild.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setSeparatBuild(Boolean.TRUE);
                } else {
                    minifyProperty.setSeparatBuild(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
        this.buildCSSMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildCSSMinify(Boolean.TRUE);
                    if(minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()){
                        skipPreExtensionCSS.setEnabled(true);
                        minifyProperty.setSkipPreExtensionCSS(Boolean.TRUE);
                        skipPreExtensionCSS.setSelected(true);
                    }
                } else {
                    minifyProperty.setBuildCSSMinify(Boolean.FALSE);
                    skipPreExtensionCSS.setEnabled(false);
                    minifyProperty.setSkipPreExtensionCSS(Boolean.FALSE);
                    skipPreExtensionCSS.setSelected(false);
                    
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
        this.buildJSMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildJSMinify(Boolean.TRUE);
                    if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
                        skipPreExtensionJS.setEnabled(true);
                        minifyProperty.setSkipPreExtensionJS(Boolean.TRUE);
                        skipPreExtensionJS.setSelected(true);
                    }
                } else {
                    minifyProperty.setBuildJSMinify(Boolean.FALSE);
                    skipPreExtensionJS.setEnabled(false);
                    minifyProperty.setSkipPreExtensionJS(Boolean.FALSE);
                    skipPreExtensionJS.setSelected(false);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
     this.skipPreExtensionCSS.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setSkipPreExtensionCSS(Boolean.TRUE);
                } else {
                    minifyProperty.setSkipPreExtensionCSS(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
        this.skipPreExtensionJS.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setSkipPreExtensionJS(Boolean.TRUE);
                } else {
                    minifyProperty.setSkipPreExtensionJS(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });








    }

   
}
