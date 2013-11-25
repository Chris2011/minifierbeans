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
            newJSFile.setSelected(Boolean.TRUE);
            preExtensionJS.setEnabled(Boolean.TRUE);
            preExtensionJS_Label.setEnabled(Boolean.TRUE);
            separatorJS.setEnabled(Boolean.TRUE);
            separatorJS_Label.setEnabled(Boolean.TRUE);
            skipPreExtensionJS.setEnabled(Boolean.TRUE);
            if (minifyProperty.getPreExtensionJS() == null) {
                minifyProperty.setPreExtensionJS("min");
            }
            this.preExtensionJS.setText(minifyProperty.getPreExtensionJS());
            if (minifyProperty.getSeparatorJS() == null) {
                minifyProperty.setSeparatorJS('.');
            }
            this.separatorJS.setText(minifyProperty.getSeparatorJS().toString());
        } else {
            newJSFile.setSelected(false);
            preExtensionJS.setEnabled(Boolean.FALSE);
            preExtensionJS_Label.setEnabled(Boolean.FALSE);
            separatorJS.setEnabled(Boolean.FALSE);
            separatorJS_Label.setEnabled(Boolean.FALSE);
            skipPreExtensionJS.setEnabled(Boolean.FALSE);
        }

        if (minifyProperty.isJsObfuscate()) {
            this.jsObfuscate.setSelected(Boolean.TRUE);
        }
//        if (minifyProperty.isPreserveSemicolon()) {
//            this.preserveSemicolon.setSelected(Boolean.TRUE);
//        }

        this.newJSFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewJSFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionJS("min");
                    preExtensionJS.setText("min");
                    minifyProperty.setSeparatorJS('.');
                    separatorJS.setText(".");
                    preExtensionJS.setEnabled(Boolean.TRUE);
                    preExtensionJS_Label.setEnabled(Boolean.TRUE);
                    separatorJS.setEnabled(Boolean.TRUE);
                    separatorJS_Label.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
                        skipPreExtensionJS.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionJS(Boolean.TRUE);
                        skipPreExtensionJS.setSelected(Boolean.TRUE);
                    }
                } else {
                    minifyProperty.setNewJSFile(Boolean.FALSE);
                    minifyProperty.setPreExtensionJS(null);
                    preExtensionJS.setText("");
                    minifyProperty.setSeparatorJS(null);
                    separatorJS.setText("");
                    preExtensionJS.setEnabled(Boolean.FALSE);
                    preExtensionJS_Label.setEnabled(Boolean.FALSE);
                    separatorJS.setEnabled(Boolean.FALSE);
                    separatorJS_Label.setEnabled(Boolean.FALSE);
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

        this.preExtensionJS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionJS.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "min";
                    preExtensionJS.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionJS(text);
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });


     
         this.separatorJS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe)  {
                String text = separatorJS.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = ".";
                } else {
                    text = String.valueOf(text.trim().charAt(0));
                }
                if (text.equals("<") || text.equals(">")
                        || text.equals(":") || text.equals("/")
                        || text.equals("\\") || text.equals("|")
                        || text.equals("?") || text.equals("*")) {
                    text = ".";
                }
                       // > : " / \ | ? *)
                
                separatorJS.setText(text);
                minifyProperty.setSeparatorJS(text.charAt(0));
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
            newCSSFile.setSelected(Boolean.TRUE);
            preExtensionCSS.setEnabled(Boolean.TRUE);
            preExtensionCSS_Label.setEnabled(Boolean.TRUE);
            separatorCSS.setEnabled(Boolean.TRUE);
            separatorCSS_Label.setEnabled(Boolean.TRUE);
            skipPreExtensionCSS.setEnabled(Boolean.TRUE);

            if (minifyProperty.getPreExtensionCSS() == null) {
                minifyProperty.setPreExtensionCSS("min");
            }
            this.preExtensionCSS.setText(minifyProperty.getPreExtensionCSS());
            if (minifyProperty.getSeparatorCSS() == null) {
                minifyProperty.setSeparatorCSS('.');
            }
            this.separatorCSS.setText(minifyProperty.getSeparatorCSS().toString());



        } else {
            newCSSFile.setSelected(false);
            preExtensionCSS.setEnabled(Boolean.FALSE);
            preExtensionCSS_Label.setEnabled(false);
            separatorCSS.setEnabled(Boolean.FALSE);
            separatorCSS_Label.setEnabled(Boolean.FALSE);
            skipPreExtensionCSS.setEnabled(false);
        }






        this.newCSSFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewCSSFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionCSS("min");
                    preExtensionCSS.setText("min");
                    minifyProperty.setSeparatorCSS('.');
                    separatorCSS.setText(".");
                    preExtensionCSS.setEnabled(Boolean.TRUE);
                    preExtensionCSS_Label.setEnabled(Boolean.TRUE);
                    separatorCSS.setEnabled(Boolean.TRUE);
                    separatorCSS_Label.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
                        skipPreExtensionCSS.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionCSS(Boolean.TRUE);
                        skipPreExtensionCSS.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewCSSFile(Boolean.FALSE);
                    minifyProperty.setPreExtensionCSS(null);
                    preExtensionCSS.setText("");
                    minifyProperty.setSeparatorCSS(null);
                    separatorCSS.setText("");
                    preExtensionCSS.setEnabled(Boolean.FALSE);
                    preExtensionCSS_Label.setEnabled(Boolean.FALSE);
                    separatorCSS.setEnabled(Boolean.FALSE);
                    separatorCSS_Label.setEnabled(Boolean.FALSE);
                    skipPreExtensionCSS.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionCSS(Boolean.FALSE);
                    skipPreExtensionCSS.setSelected(Boolean.FALSE);

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


        this.preExtensionCSS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionCSS.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "min";
                    preExtensionCSS.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionCSS(text);
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });


        this.separatorCSS.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = separatorCSS.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = ".";
                } else {
                    text = String.valueOf(text.trim().charAt(0));
                }
                 if (text.equals("<") || text.equals(">")
                        || text.equals(":") || text.equals("/")
                        || text.equals("\\") || text.equals("|")
                        || text.equals("?") || text.equals("*")) {
                    text = ".";
                }
                separatorCSS.setText(text);
                minifyProperty.setSeparatorCSS(text.charAt(0));
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
           
//---------------------------------------------------------------------------------------------//
        
        /* HTML Minify Setting */
        
        if (minifyProperty.isNewHTMLFile()) {
            newHTMLFile.setSelected(Boolean.TRUE);
            preExtensionHTML.setEnabled(Boolean.TRUE);
            preExtensionHTML_Label.setEnabled(Boolean.TRUE);
            separatorHTML.setEnabled(Boolean.TRUE);
            separatorHTML_Label.setEnabled(Boolean.TRUE);
            skipPreExtensionHTML.setEnabled(Boolean.TRUE);

            if (minifyProperty.getPreExtensionHTML() == null) {
                minifyProperty.setPreExtensionHTML("min");
            }
            this.preExtensionHTML.setText(minifyProperty.getPreExtensionHTML());
            if (minifyProperty.getSeparatorHTML() == null) {
                minifyProperty.setSeparatorHTML('.');
            }
            this.separatorHTML.setText(minifyProperty.getSeparatorHTML().toString());



        } else {
            newHTMLFile.setSelected(false);
            preExtensionHTML.setEnabled(Boolean.FALSE);
            preExtensionHTML_Label.setEnabled(false);
            separatorHTML.setEnabled(Boolean.FALSE);
            separatorHTML_Label.setEnabled(Boolean.FALSE);
            skipPreExtensionHTML.setEnabled(false);
        }






        this.newHTMLFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewHTMLFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionHTML("min");
                    preExtensionHTML.setText("min");
                    minifyProperty.setSeparatorHTML('.');
                    separatorHTML.setText(".");
                    preExtensionHTML.setEnabled(Boolean.TRUE);
                    preExtensionHTML_Label.setEnabled(Boolean.TRUE);
                    separatorHTML.setEnabled(Boolean.TRUE);
                    separatorHTML_Label.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
                        skipPreExtensionHTML.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionHTML(Boolean.TRUE);
                        skipPreExtensionHTML.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewHTMLFile(Boolean.FALSE);
                    minifyProperty.setPreExtensionHTML(null);
                    preExtensionHTML.setText("");
                    minifyProperty.setSeparatorHTML(null);
                    separatorHTML.setText("");
                    preExtensionHTML.setEnabled(Boolean.FALSE);
                    preExtensionHTML_Label.setEnabled(Boolean.FALSE);
                    separatorHTML.setEnabled(Boolean.FALSE);
                    separatorHTML_Label.setEnabled(Boolean.FALSE);
                    skipPreExtensionHTML.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionHTML(Boolean.FALSE);
                    skipPreExtensionHTML.setSelected(Boolean.FALSE);

                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });

   



        this.preExtensionHTML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionHTML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "min";
                    preExtensionHTML.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionHTML(text);
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });


        this.separatorHTML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = separatorHTML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = ".";
                } else {
                    text = String.valueOf(text.trim().charAt(0));
                }
                 if (text.equals("<") || text.equals(">")
                        || text.equals(":") || text.equals("/")
                        || text.equals("\\") || text.equals("|")
                        || text.equals("?") || text.equals("*")) {
                    text = ".";
                }
                separatorHTML.setText(text);
                minifyProperty.setSeparatorHTML(text.charAt(0));
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });

        
             
         if (minifyProperty.isBuildInternalCSSMinify()) {
            this.buildInternalCSSMinify.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildInternalJSMinify()) {
            this.buildInternalJSMinify.setSelected(Boolean.TRUE);
        }
 this.buildInternalCSSMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildInternalCSSMinify(Boolean.TRUE);
                } else {
                    minifyProperty.setBuildInternalCSSMinify(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        }); 
 this.buildInternalJSMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildInternalJSMinify(Boolean.TRUE);
                } else {
                    minifyProperty.setBuildInternalJSMinify(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
        
        
        
      //-----------------------------------------------------------------------------------------------//  
        
        
        
        
        /** Build Setting**/
        if (minifyProperty.isSeparatBuild()) {
            this.separatBuild.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildCSSMinify()) {
            this.buildCSSMinify.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildJSMinify()) {
            this.buildJSMinify.setSelected(Boolean.TRUE);
        }

          if (minifyProperty.isBuildHTMLMinify()) {
            this.buildHTMLMinify.setSelected(Boolean.TRUE);
        }

        
        
        if (minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
            skipPreExtensionCSS.setEnabled(Boolean.TRUE);
            if (minifyProperty.isSkipPreExtensionCSS()) {
                skipPreExtensionCSS.setSelected(Boolean.TRUE);
            } else {
                skipPreExtensionCSS.setSelected(Boolean.FALSE);
            }
        } else {
            this.skipPreExtensionCSS.setEnabled(Boolean.FALSE);
            skipPreExtensionCSS.setSelected(Boolean.FALSE);
        }

        if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
            this.skipPreExtensionJS.setEnabled(Boolean.TRUE);
            if (minifyProperty.isSkipPreExtensionJS()) {
                skipPreExtensionJS.setSelected(Boolean.TRUE);
            } else {
                skipPreExtensionJS.setSelected(Boolean.FALSE);
            }
        } else {
            this.skipPreExtensionJS.setEnabled(Boolean.FALSE);
            skipPreExtensionJS.setSelected(Boolean.FALSE);
        }


   if (minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
            skipPreExtensionHTML.setEnabled(Boolean.TRUE);
            if (minifyProperty.isSkipPreExtensionHTML()) {
                skipPreExtensionHTML.setSelected(Boolean.TRUE);
            } else {
                skipPreExtensionHTML.setSelected(Boolean.FALSE);
            }
        } else {
            this.skipPreExtensionHTML.setEnabled(Boolean.FALSE);
            skipPreExtensionHTML.setSelected(Boolean.FALSE);
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
                    if (minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
                        skipPreExtensionCSS.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionCSS(Boolean.TRUE);
                        skipPreExtensionCSS.setSelected(Boolean.TRUE);
                    }
                } else {
                    minifyProperty.setBuildCSSMinify(Boolean.FALSE);
                    skipPreExtensionCSS.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionCSS(Boolean.FALSE);
                    skipPreExtensionCSS.setSelected(Boolean.FALSE);

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
        this.buildJSMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildJSMinify(Boolean.TRUE);
                    if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
                        skipPreExtensionJS.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionJS(Boolean.TRUE);
                        skipPreExtensionJS.setSelected(Boolean.TRUE);
                    }
                } else {
                    minifyProperty.setBuildJSMinify(Boolean.FALSE);
                    skipPreExtensionJS.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionJS(Boolean.FALSE);
                    skipPreExtensionJS.setSelected(Boolean.FALSE);
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

        
        
           this.buildHTMLMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildHTMLMinify(Boolean.TRUE);
                    if (minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
                        skipPreExtensionHTML.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionHTML(Boolean.TRUE);
                        skipPreExtensionHTML.setSelected(Boolean.TRUE);
                    }
                } else {
                    minifyProperty.setBuildHTMLMinify(Boolean.FALSE);
                    skipPreExtensionHTML.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionHTML(Boolean.FALSE);
                    skipPreExtensionHTML.setSelected(Boolean.FALSE);

                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });
   this.skipPreExtensionHTML.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setSkipPreExtensionHTML(Boolean.TRUE);
                } else {
                    minifyProperty.setSkipPreExtensionHTML(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });



        if (minifyProperty.isAppendLogToFile()) {
            this.addLogToFile.setSelected(Boolean.TRUE);
        }
        this.addLogToFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAppendLogToFile(Boolean.TRUE);
                } else {
                    minifyProperty.setAppendLogToFile(Boolean.FALSE);
                }
                minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });


    }
}
