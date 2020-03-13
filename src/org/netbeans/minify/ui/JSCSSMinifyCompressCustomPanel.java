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
import org.netbeans.minify.cssnano.ui.options.CssNanoCliOptionsPanel;

public final class JSCSSMinifyCompressCustomPanel extends JSCSSMinifyCompressPanel {

    private MinifyProperty minifyProperty;

    JSCSSMinifyCompressCustomPanel(JSCSSMinifyCompressOptionsPanelController controller) {
        super(controller);

        /* New CSSNano Settings */
        cssNanoPanel.add(new CssNanoCliOptionsPanel());

        minifyProperty = MinifyProperty.getInstance();

        newJSFile1.setSelected(minifyProperty.isNewJSFile());
        preExtensionJS1.setEnabled(minifyProperty.isNewJSFile());
        preExtensionJS_Label1.setEnabled(minifyProperty.isNewJSFile());
        separatorJS1.setEnabled(minifyProperty.isNewJSFile());
        separatorJS_Label1.setEnabled(minifyProperty.isNewJSFile());
        skipPreExtensionJS.setEnabled(minifyProperty.isNewJSFile());
        preExtensionJS1.setText(minifyProperty.getPreExtensionJS());
        separatorJS1.setText(minifyProperty.getSeparatorJS().toString());

        if (minifyProperty.isJsObfuscate()) {
            this.jsObfuscate1.setSelected(Boolean.TRUE);
        }

        autoMinifyJS1.setSelected(minifyProperty.isAutoMinifyJS());
        headerEditorPaneJS1.setText(minifyProperty.getHeaderJS());

        headerEditorPaneJS1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneJS1.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneJS1.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderJS(text);
            }
        });
        this.autoMinifyJS1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyJS(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyJS(Boolean.FALSE);
                }
            }
        });

        this.newJSFile1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewJSFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionJS("min");
                    preExtensionJS1.setText("min");
                    minifyProperty.setSeparatorJS('.');
                    separatorJS1.setText(".");
                    preExtensionJS1.setEnabled(Boolean.TRUE);
                    preExtensionJS_Label1.setEnabled(Boolean.TRUE);
                    separatorJS1.setEnabled(Boolean.TRUE);
                    separatorJS_Label1.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
                        skipPreExtensionJS.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionJS(Boolean.TRUE);
                        skipPreExtensionJS.setSelected(Boolean.TRUE);
                    }
                } else {
                    minifyProperty.setNewJSFile(Boolean.FALSE);
                    preExtensionJS1.setEnabled(Boolean.FALSE);
                    preExtensionJS_Label1.setEnabled(Boolean.FALSE);
                    separatorJS1.setEnabled(Boolean.FALSE);
                    separatorJS_Label1.setEnabled(Boolean.FALSE);
                    skipPreExtensionJS.setEnabled(false);
                    minifyProperty.setSkipPreExtensionJS(Boolean.FALSE);
                    skipPreExtensionJS.setSelected(false);
                }
            }
        });

        this.preExtensionJS1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionJS1.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "min";
                    preExtensionJS1.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionJS(text);
            }
        });

        this.separatorJS1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = separatorJS1.getText();
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
                separatorJS1.setText(text);
                minifyProperty.setSeparatorJS(text.charAt(0));
            }
        });

        this.jsObfuscate1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setJsObfuscate(Boolean.TRUE);
                } else {
                    minifyProperty.setJsObfuscate(Boolean.FALSE);
                }
            }
        });

        newCSSFile1.setSelected(minifyProperty.isNewCSSFile());
        preExtensionCSS1.setEnabled(minifyProperty.isNewCSSFile());
        preExtensionCSS_Label1.setEnabled(minifyProperty.isNewCSSFile());
        separatorCSS1.setEnabled(minifyProperty.isNewCSSFile());
        separatorCSS_Label1.setEnabled(minifyProperty.isNewCSSFile());
        skipPreExtensionCSS.setEnabled(minifyProperty.isNewCSSFile());
        this.preExtensionCSS1.setText(minifyProperty.getPreExtensionCSS());
        this.separatorCSS1.setText(minifyProperty.getSeparatorCSS().toString());

        autoMinifyCSS1.setSelected(minifyProperty.isAutoMinifyCSS());
        headerEditorPaneCSS1.setText(minifyProperty.getHeaderCSS());

        headerEditorPaneCSS1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneCSS1.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneCSS1.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderCSS(text);
            }
        });
        this.autoMinifyCSS1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyCSS(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyCSS(Boolean.FALSE);
                }
            }
        });
        this.newCSSFile1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewCSSFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionCSS("min");
                    preExtensionCSS1.setText("min");
                    minifyProperty.setSeparatorCSS('.');
                    separatorCSS1.setText(".");
                    preExtensionCSS1.setEnabled(Boolean.TRUE);
                    preExtensionCSS_Label1.setEnabled(Boolean.TRUE);
                    separatorCSS1.setEnabled(Boolean.TRUE);
                    separatorCSS_Label1.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
                        skipPreExtensionCSS.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionCSS(Boolean.TRUE);
                        skipPreExtensionCSS.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewCSSFile(Boolean.FALSE);
                    preExtensionCSS1.setEnabled(Boolean.FALSE);
                    preExtensionCSS_Label1.setEnabled(Boolean.FALSE);
                    separatorCSS1.setEnabled(Boolean.FALSE);
                    separatorCSS_Label1.setEnabled(Boolean.FALSE);
                    skipPreExtensionCSS.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionCSS(Boolean.FALSE);
                    skipPreExtensionCSS.setSelected(Boolean.FALSE);
                }
            }
        });

        this.preExtensionCSS1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionCSS1.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "min";
                    preExtensionCSS1.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionCSS(text);
            }
        });

        this.separatorCSS1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = separatorCSS1.getText();
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
                separatorCSS1.setText(text);
                minifyProperty.setSeparatorCSS(text.charAt(0));
            }
        });

//---------------------------------------------------------------------------------------------//

        /* HTML Minify Setting */
        newHTMLFile.setSelected(minifyProperty.isNewHTMLFile());
        preExtensionHTML.setEnabled(minifyProperty.isNewHTMLFile());
        preExtensionHTML_Label.setEnabled(minifyProperty.isNewHTMLFile());
        separatorHTML.setEnabled(minifyProperty.isNewHTMLFile());
        separatorHTML_Label.setEnabled(minifyProperty.isNewHTMLFile());
        skipPreExtensionHTML.setEnabled(minifyProperty.isNewHTMLFile());
        this.preExtensionHTML.setText(minifyProperty.getPreExtensionHTML());
        this.separatorHTML.setText(minifyProperty.getSeparatorHTML().toString());
        autoMinifyHTML.setSelected(minifyProperty.isAutoMinifyHTML());
        headerEditorPaneHTML.setText(minifyProperty.getHeaderHTML());
        headerEditorPaneHTML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneHTML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneHTML.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderHTML(text);
            }
        });
        this.autoMinifyHTML.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyHTML(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyHTML(Boolean.FALSE);
                }
            }
        });
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
//                    minifyProperty.setPreExtensionHTML(null);
//                    preExtensionHTML.setText("");
//                    minifyProperty.setSeparatorHTML(null);
//                    separatorHTML.setText("");
                    preExtensionHTML.setEnabled(Boolean.FALSE);
                    preExtensionHTML_Label.setEnabled(Boolean.FALSE);
                    separatorHTML.setEnabled(Boolean.FALSE);
                    separatorHTML_Label.setEnabled(Boolean.FALSE);
                    skipPreExtensionHTML.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionHTML(Boolean.FALSE);
                    skipPreExtensionHTML.setSelected(Boolean.FALSE);

                }
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });

        //-----------------------------------------------------------------------------------------------//
        /**
         * XML Setting *
         */
        newXMLFile.setSelected(minifyProperty.isNewXMLFile());
        preExtensionXML.setEnabled(minifyProperty.isNewXMLFile());
        preExtensionXML_Label.setEnabled(minifyProperty.isNewXMLFile());
        separatorXML.setEnabled(minifyProperty.isNewXMLFile());
        separatorXML_Label.setEnabled(minifyProperty.isNewXMLFile());
        skipPreExtensionXML.setEnabled(minifyProperty.isNewXMLFile());
        this.preExtensionXML.setText(minifyProperty.getPreExtensionXML());
        this.separatorXML.setText(minifyProperty.getSeparatorXML().toString());
        autoMinifyXML.setSelected(minifyProperty.isAutoMinifyXML());
        headerEditorPaneXML.setText(minifyProperty.getHeaderXML());

        headerEditorPaneXML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneXML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneXML.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderXML(text);
            }
        });
        this.autoMinifyXML.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyXML(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyXML(Boolean.FALSE);
                }
            }
        });

        this.newXMLFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewXMLFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionXML("min");
                    preExtensionXML.setText("min");
                    minifyProperty.setSeparatorXML('.');
                    separatorXML.setText(".");
                    preExtensionXML.setEnabled(Boolean.TRUE);
                    preExtensionXML_Label.setEnabled(Boolean.TRUE);
                    separatorXML.setEnabled(Boolean.TRUE);
                    separatorXML_Label.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildXMLMinify() && minifyProperty.isNewXMLFile()) {
                        skipPreExtensionXML.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionXML(Boolean.TRUE);
                        skipPreExtensionXML.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewXMLFile(Boolean.FALSE);
                    preExtensionXML.setEnabled(Boolean.FALSE);
                    preExtensionXML_Label.setEnabled(Boolean.FALSE);
                    separatorXML.setEnabled(Boolean.FALSE);
                    separatorXML_Label.setEnabled(Boolean.FALSE);
                    skipPreExtensionXML.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionXML(Boolean.FALSE);
                    skipPreExtensionXML.setSelected(Boolean.FALSE);
                }
            }
        });

        this.preExtensionXML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionXML.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "min";
                    preExtensionXML.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionXML(text);
            }
        });

        this.separatorXML.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = separatorXML.getText();
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
                separatorXML.setText(text);
                minifyProperty.setSeparatorXML(text.charAt(0));
            }
        });

//---------------------------------------------------------------------------------------------//
        /**
         * JSON Setting *
         */
        newJSONFile.setSelected(minifyProperty.isNewJSONFile());
        preExtensionJSON.setEnabled(minifyProperty.isNewJSONFile());
        preExtensionJSON_Label.setEnabled(minifyProperty.isNewJSONFile());
        separatorJSON.setEnabled(minifyProperty.isNewJSONFile());
        separatorJSON_Label.setEnabled(minifyProperty.isNewJSONFile());
        skipPreExtensionJSON.setEnabled(minifyProperty.isNewJSONFile());
        this.preExtensionJSON.setText(minifyProperty.getPreExtensionJSON());
        this.separatorJSON.setText(minifyProperty.getSeparatorJSON().toString());
        autoMinifyJSON.setSelected(minifyProperty.isAutoMinifyJSON());
        headerEditorPaneJSON.setText(minifyProperty.getHeaderJSON());

        headerEditorPaneJSON.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = headerEditorPaneJSON.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "";
                    headerEditorPaneJSON.setText("");
                } else {
                    text = text.trim();
                }
                minifyProperty.setHeaderJSON(text);
            }
        });
        this.autoMinifyJSON.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAutoMinifyJSON(Boolean.TRUE);
                } else {
                    minifyProperty.setAutoMinifyJSON(Boolean.FALSE);
                }
            }
        });

        this.newJSONFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setNewJSONFile(Boolean.TRUE);
                    minifyProperty.setPreExtensionJSON("min");
                    preExtensionJSON.setText("min");
                    minifyProperty.setSeparatorJSON('.');
                    separatorJSON.setText(".");
                    preExtensionJSON.setEnabled(Boolean.TRUE);
                    preExtensionJSON_Label.setEnabled(Boolean.TRUE);
                    separatorJSON.setEnabled(Boolean.TRUE);
                    separatorJSON_Label.setEnabled(Boolean.TRUE);
                    if (minifyProperty.isBuildJSONMinify() && minifyProperty.isNewJSONFile()) {
                        skipPreExtensionJSON.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionJSON(Boolean.TRUE);
                        skipPreExtensionJSON.setSelected(Boolean.TRUE);
                    }

                } else {
                    minifyProperty.setNewJSONFile(Boolean.FALSE);
                    preExtensionJSON.setEnabled(Boolean.FALSE);
                    preExtensionJSON_Label.setEnabled(Boolean.FALSE);
                    separatorJSON.setEnabled(Boolean.FALSE);
                    separatorJSON_Label.setEnabled(Boolean.FALSE);
                    skipPreExtensionJSON.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionJSON(Boolean.FALSE);
                    skipPreExtensionJSON.setSelected(Boolean.FALSE);
                }
            }
        });

        this.preExtensionJSON.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = preExtensionJSON.getText();
                if (text == null || text.trim().isEmpty()) {
                    text = "min";
                    preExtensionJSON.setText(text);
                } else {
                    text = text.trim();
                }
                minifyProperty.setPreExtensionJSON(text);
            }
        });

        this.separatorJSON.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                String text = separatorJSON.getText();
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
                separatorJSON.setText(text);
                minifyProperty.setSeparatorJSON(text.charAt(0));
            }
        });

//---------------------------------------------------------------------------------------------//
        /**
         * Build Setting*
         */
        if (minifyProperty.isSeparatBuild()) {
            this.separatBuild.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildJSMinify()) {
            this.buildJSMinify.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildCSSMinify()) {
            this.buildCSSMinify.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildHTMLMinify()) {
            this.buildHTMLMinify.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildXMLMinify()) {
            this.buildXMLMinify.setSelected(Boolean.TRUE);
        }

        if (minifyProperty.isBuildJSONMinify()) {
            this.buildJSONMinify.setSelected(Boolean.TRUE);
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

        if (minifyProperty.isBuildXMLMinify() && minifyProperty.isNewXMLFile()) {
            skipPreExtensionXML.setEnabled(Boolean.TRUE);
            if (minifyProperty.isSkipPreExtensionXML()) {
                skipPreExtensionXML.setSelected(Boolean.TRUE);
            } else {
                skipPreExtensionXML.setSelected(Boolean.FALSE);
            }
        } else {
            this.skipPreExtensionXML.setEnabled(Boolean.FALSE);
            skipPreExtensionXML.setSelected(Boolean.FALSE);
        }

        if (minifyProperty.isBuildJSONMinify() && minifyProperty.isNewJSONFile()) {
            skipPreExtensionJSON.setEnabled(Boolean.TRUE);
            if (minifyProperty.isSkipPreExtensionJSON()) {
                skipPreExtensionJSON.setSelected(Boolean.TRUE);
            } else {
                skipPreExtensionJSON.setSelected(Boolean.FALSE);
            }
        } else {
            this.skipPreExtensionJSON.setEnabled(Boolean.FALSE);
            skipPreExtensionJSON.setSelected(Boolean.FALSE);
        }

        this.separatBuild.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setSeparatBuild(Boolean.TRUE);
                } else {
                    minifyProperty.setSeparatBuild(Boolean.FALSE);
                }
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
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
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });

        this.buildXMLMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildXMLMinify(Boolean.TRUE);
                    if (minifyProperty.isBuildXMLMinify() && minifyProperty.isNewXMLFile()) {
                        skipPreExtensionXML.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionXML(Boolean.TRUE);
                        skipPreExtensionXML.setSelected(Boolean.TRUE);
                    }
                } else {
                    minifyProperty.setBuildXMLMinify(Boolean.FALSE);
                    skipPreExtensionXML.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionXML(Boolean.FALSE);
                    skipPreExtensionXML.setSelected(Boolean.FALSE);
                }
            }
        });
        this.skipPreExtensionXML.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setSkipPreExtensionXML(Boolean.TRUE);
                } else {
                    minifyProperty.setSkipPreExtensionXML(Boolean.FALSE);
                }
            }
        });

        this.buildJSONMinify.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setBuildJSONMinify(Boolean.TRUE);
                    if (minifyProperty.isBuildJSONMinify() && minifyProperty.isNewJSONFile()) {
                        skipPreExtensionJSON.setEnabled(Boolean.TRUE);
                        minifyProperty.setSkipPreExtensionJSON(Boolean.TRUE);
                        skipPreExtensionJSON.setSelected(Boolean.TRUE);
                    }
                } else {
                    minifyProperty.setBuildJSONMinify(Boolean.FALSE);
                    skipPreExtensionJSON.setEnabled(Boolean.FALSE);
                    minifyProperty.setSkipPreExtensionJSON(Boolean.FALSE);
                    skipPreExtensionJSON.setSelected(Boolean.FALSE);
                }
            }
        });
        this.skipPreExtensionJSON.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setSkipPreExtensionJSON(Boolean.TRUE);
                } else {
                    minifyProperty.setSkipPreExtensionJSON(Boolean.FALSE);
                }
            }
        });

        if (minifyProperty.isAppendLogToFile()) {
            this.addLogToFile.setSelected(Boolean.TRUE);
        } else {
            this.addLogToFile.setSelected(Boolean.FALSE);
        }
        this.addLogToFile.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setAppendLogToFile(Boolean.TRUE);
                } else {
                    minifyProperty.setAppendLogToFile(Boolean.FALSE);
                }
            }
        });

        if (minifyProperty.isEnableOutputLogAlert()) {
            this.enableOutputLogAlert.setSelected(Boolean.TRUE);
        } else {
            this.enableOutputLogAlert.setSelected(Boolean.FALSE);
        }
        this.enableOutputLogAlert.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setEnableOutputLogAlert(Boolean.TRUE);
                } else {
                    minifyProperty.setEnableOutputLogAlert(Boolean.FALSE);
                }
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });

        if (minifyProperty.isEnableShortKeyAction()) {
            this.enableShortKeyAction.setSelected(Boolean.TRUE);
            this.enableShortKeyActionConfirmBox.setEnabled(Boolean.TRUE);
//            this.enableShortKeyActionConfirmBox.setSelected(Boolean.TRUE);
//            minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.TRUE);
        } else {
            this.enableShortKeyAction.setSelected(Boolean.FALSE);
            this.enableShortKeyActionConfirmBox.setEnabled(Boolean.FALSE);
            this.enableShortKeyActionConfirmBox.setSelected(Boolean.FALSE);
            minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.FALSE);
        }
        this.enableShortKeyAction.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setEnableShortKeyAction(Boolean.TRUE);
                    JSCSSMinifyCompressCustomPanel.this.enableShortKeyActionConfirmBox.setEnabled(Boolean.TRUE);
                    JSCSSMinifyCompressCustomPanel.this.enableShortKeyActionConfirmBox.setSelected(Boolean.TRUE);
                    minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.TRUE);
                } else {
                    minifyProperty.setEnableShortKeyAction(Boolean.FALSE);
                    JSCSSMinifyCompressCustomPanel.this.enableShortKeyActionConfirmBox.setEnabled(Boolean.FALSE);
                    JSCSSMinifyCompressCustomPanel.this.enableShortKeyActionConfirmBox.setSelected(Boolean.FALSE);
                    minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.FALSE);
                }
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });

        if (minifyProperty.isEnableShortKeyActionConfirmBox()) {
            this.enableShortKeyActionConfirmBox.setSelected(Boolean.TRUE);
        } else {
            this.enableShortKeyActionConfirmBox.setSelected(Boolean.FALSE);
        }
        this.enableShortKeyActionConfirmBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.TRUE);
                } else {
                    minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.FALSE);
                }
                //minifyPropertyController.writeMinifyProperty(minifyProperty);
            }
        });

    }

    public void load() {
//      minifyProperty.load();//loaded on MinifyProperty initiation time
    }

    public void store() {
        minifyProperty.store();
    }

    public void cancel() {
        minifyProperty.cancel();
    }

    public boolean valid() {
        return true;
    }
}
