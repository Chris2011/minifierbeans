package io.github.chris2011.netbeans.minifierbeans.folder.ui.options;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JPanel;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;

@OptionsPanelController.Keywords(keywords = {"folder", "#FolderOptionsPanel.Keyword1"},
        location = "Html5", tabTitle = "#CTL_OptionsPanel.title")
@NbBundle.Messages({
    "CTL_OptionsPanel.title=Minification",
    "FolderOptionsPanel.Keyword1=Minify Folder",})
public final class FolderOptionsPanel extends JPanel {

    private String[] keywords;

    public FolderOptionsPanel() {
        initComponents();

        init();
    }

    @Override
    public void addNotify() {
        super.addNotify();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
    }

    public Collection<String> getKeywords() {
        if (keywords == null) {
            keywords = new String[]{
                "Folder",
                Bundle.FolderOptionsPanel_Keyword1().toUpperCase()
            };
        }

        return Collections.unmodifiableList(Arrays.asList(keywords));
    }

    private void init() {
//        DocumentListener defaultDocumentListener = new DefaultDocumentListener();

//        minifyProperty = MinifyOptions.getInstance();
//        if (minifyProperty.isSeparatBuild()) {
//            this.separatBuild.setSelected(Boolean.TRUE);
//        }
//
//        if (minifyProperty.isBuildJSMinify()) {
//            this.buildJSMinify.setSelected(Boolean.TRUE);
//        }
//
//        if (minifyProperty.isBuildCSSMinify()) {
//            this.buildCSSMinify.setSelected(Boolean.TRUE);
//        }
//
//        if (minifyProperty.isBuildHTMLMinify()) {
//            this.buildHTMLMinify.setSelected(Boolean.TRUE);
//        }
//
//        if (minifyProperty.isBuildXMLMinify()) {
//            this.buildXMLMinify.setSelected(Boolean.TRUE);
//        }
//
//        if (minifyProperty.isBuildJSONMinify()) {
//            this.buildJSONMinify.setSelected(Boolean.TRUE);
//        }
//        if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
//            this.skipPreExtensionJS.setEnabled(Boolean.TRUE);
//
//            if (minifyProperty.isSkipPreExtensionJS()) {
//                skipPreExtensionJS.setSelected(Boolean.TRUE);
//            } else {
//                skipPreExtensionJS.setSelected(Boolean.FALSE);
//            }
//        } else {
//            this.skipPreExtensionJS.setEnabled(Boolean.FALSE);
//            skipPreExtensionJS.setSelected(Boolean.FALSE);
//        }
//        if (minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
//        if (minifyProperty.isBuildCSSMinify()) {
//            skipPreExtensionCSS.setEnabled(Boolean.TRUE);
//
//            if (minifyProperty.isSkipPreExtensionCSS()) {
//                skipPreExtensionCSS.setSelected(Boolean.TRUE);
//            } else {
//                skipPreExtensionCSS.setSelected(Boolean.FALSE);
//            }
//        } else {
//            this.skipPreExtensionCSS.setEnabled(Boolean.FALSE);
//            skipPreExtensionCSS.setSelected(Boolean.FALSE);
//        }
//        if (minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
//            skipPreExtensionHTML.setEnabled(Boolean.TRUE);
//
//            if (minifyProperty.isSkipPreExtensionHTML()) {
//                skipPreExtensionHTML.setSelected(Boolean.TRUE);
//            } else {
//                skipPreExtensionHTML.setSelected(Boolean.FALSE);
//            }
//        } else {
//            this.skipPreExtensionHTML.setEnabled(Boolean.FALSE);
//            skipPreExtensionHTML.setSelected(Boolean.FALSE);
//        }
//
//        if (minifyProperty.isBuildXMLMinify() && minifyProperty.isNewXMLFile()) {
//            skipPreExtensionXML.setEnabled(Boolean.TRUE);
//
//            if (minifyProperty.isSkipPreExtensionXML()) {
//                skipPreExtensionXML.setSelected(Boolean.TRUE);
//            } else {
//                skipPreExtensionXML.setSelected(Boolean.FALSE);
//            }
//        } else {
//            this.skipPreExtensionXML.setEnabled(Boolean.FALSE);
//            skipPreExtensionXML.setSelected(Boolean.FALSE);
//        }
//
//        if (minifyProperty.isBuildJSONMinify() && minifyProperty.isNewJSONFile()) {
//            skipPreExtensionJSON.setEnabled(Boolean.TRUE);
//
//            if (minifyProperty.isSkipPreExtensionJSON()) {
//                skipPreExtensionJSON.setSelected(Boolean.TRUE);
//            } else {
//                skipPreExtensionJSON.setSelected(Boolean.FALSE);
//            }
//        } else {
//            this.skipPreExtensionJSON.setEnabled(Boolean.FALSE);
//            skipPreExtensionJSON.setSelected(Boolean.FALSE);
//        }
//        this.separatBuildFolder.addItemListener((ItemEvent e) -> {
//            minifyProperty.setSeparatBuild(e.getStateChange() == ItemEvent.SELECTED);
//        });
//        this.buildJSMinify.addItemListener((ItemEvent e) -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                minifyProperty.setBuildJSMinify(Boolean.TRUE);
//
//                if (minifyProperty.isBuildJSMinify() && minifyProperty.isNewJSFile()) {
//                    skipPreExtensionJS.setEnabled(Boolean.TRUE);
//                    minifyProperty.setSkipPreExtensionJS(Boolean.TRUE);
//                    skipPreExtensionJS.setSelected(Boolean.TRUE);
//                }
//            } else {
//                minifyProperty.setBuildJSMinify(Boolean.FALSE);
//                skipPreExtensionJS.setEnabled(Boolean.FALSE);
//                minifyProperty.setSkipPreExtensionJS(Boolean.FALSE);
//                skipPreExtensionJS.setSelected(Boolean.FALSE);
//            }
//            //minifyPropertyController.writeMinifyProperty(minifyProperty);
//        });
//        this.skipPreExtensionJS.addItemListener((ItemEvent e) -> {
//            minifyProperty.setSkipPreExtensionJS(e.getStateChange() == ItemEvent.SELECTED);
//            //minifyPropertyController.writeMinifyProperty(minifyProperty);
//        });
//
//        this.buildCSSMinify.addItemListener((ItemEvent e) -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                minifyProperty.setBuildCSSMinify(Boolean.TRUE);
//
////                if (minifyProperty.isBuildCSSMinify() && minifyProperty.isNewCSSFile()) {
//                if (minifyProperty.isBuildCSSMinify()) {
//                    skipPreExtensionCSS.setEnabled(Boolean.TRUE);
//                    minifyProperty.setSkipPreExtensionCSS(Boolean.TRUE);
//                    skipPreExtensionCSS.setSelected(Boolean.TRUE);
//                }
//            } else {
//                minifyProperty.setBuildCSSMinify(Boolean.FALSE);
//                skipPreExtensionCSS.setEnabled(Boolean.FALSE);
//                minifyProperty.setSkipPreExtensionCSS(Boolean.FALSE);
//                skipPreExtensionCSS.setSelected(Boolean.FALSE);
//            }
//        });
//        this.skipPreExtensionCSS.addItemListener((ItemEvent e) -> {
//            minifyProperty.setSkipPreExtensionCSS(e.getStateChange() == ItemEvent.SELECTED);
//        });
//
//        this.buildHTMLMinify.addItemListener((ItemEvent e) -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                minifyProperty.setBuildHTMLMinify(Boolean.TRUE);
//
//                if (minifyProperty.isBuildHTMLMinify() && minifyProperty.isNewHTMLFile()) {
//                    skipPreExtensionHTML.setEnabled(Boolean.TRUE);
//                    minifyProperty.setSkipPreExtensionHTML(Boolean.TRUE);
//                    skipPreExtensionHTML.setSelected(Boolean.TRUE);
//                }
//            } else {
//                minifyProperty.setBuildHTMLMinify(Boolean.FALSE);
//                skipPreExtensionHTML.setEnabled(Boolean.FALSE);
//                minifyProperty.setSkipPreExtensionHTML(Boolean.FALSE);
//                skipPreExtensionHTML.setSelected(Boolean.FALSE);
//
//            }
//            //minifyPropertyController.writeMinifyProperty(minifyProperty);
//        });
//        this.skipPreExtensionHTML.addItemListener((ItemEvent e) -> {
//            minifyProperty.setSkipPreExtensionHTML(e.getStateChange() == ItemEvent.SELECTED);
//            //minifyPropertyController.writeMinifyProperty(minifyProperty);
//        });
//
//        this.buildXMLMinify.addItemListener((ItemEvent e) -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                minifyProperty.setBuildXMLMinify(Boolean.TRUE);
//
//                if (minifyProperty.isBuildXMLMinify() && minifyProperty.isNewXMLFile()) {
//                    skipPreExtensionXML.setEnabled(Boolean.TRUE);
//                    minifyProperty.setSkipPreExtensionXML(Boolean.TRUE);
//                    skipPreExtensionXML.setSelected(Boolean.TRUE);
//                }
//            } else {
//                minifyProperty.setBuildXMLMinify(Boolean.FALSE);
//                skipPreExtensionXML.setEnabled(Boolean.FALSE);
//                minifyProperty.setSkipPreExtensionXML(Boolean.FALSE);
//                skipPreExtensionXML.setSelected(Boolean.FALSE);
//            }
//        });
//
//        this.skipPreExtensionXML.addItemListener((ItemEvent e) -> {
//            minifyProperty.setSkipPreExtensionXML(e.getStateChange() == ItemEvent.SELECTED);
//        });
//        this.buildJSONMinify.addItemListener((ItemEvent e) -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                minifyProperty.setBuildJSONMinify(Boolean.TRUE);
//
//                if (minifyProperty.isBuildJSONMinify() && minifyProperty.isNewJSONFile()) {
//                    skipPreExtensionJSON.setEnabled(Boolean.TRUE);
//                    minifyProperty.setSkipPreExtensionJSON(Boolean.TRUE);
//                    skipPreExtensionJSON.setSelected(Boolean.TRUE);
//                }
//            } else {
//                minifyProperty.setBuildJSONMinify(Boolean.FALSE);
//                skipPreExtensionJSON.setEnabled(Boolean.FALSE);
//                minifyProperty.setSkipPreExtensionJSON(Boolean.FALSE);
//                skipPreExtensionJSON.setSelected(Boolean.FALSE);
//            }
//        });
//
//        this.skipPreExtensionJSON.addItemListener((ItemEvent e) -> {
//            minifyProperty.setSkipPreExtensionJSON(e.getStateChange() == ItemEvent.SELECTED);
//        });
//
//        this.addLogToFile.setSelected(minifyProperty.isAppendLogToFile());
//
//        this.addLogToFile.addItemListener((ItemEvent e) -> {
//            minifyProperty.setAppendLogToFile(e.getStateChange() == ItemEvent.SELECTED);
//        });
//
//        this.enableOutputLogAlert.setSelected(minifyProperty.isEnableOutputLogAlert());
//
//        this.enableOutputLogAlert.addItemListener((ItemEvent e) -> {
//            minifyProperty.setEnableOutputLogAlert(e.getStateChange() == ItemEvent.SELECTED);
//            //minifyPropertyController.writeMinifyProperty(minifyProperty);
//        });
//        if (minifyProperty.isEnableShortKeyAction()) {
//            this.enableShortKeyAction.setSelected(Boolean.TRUE);
//            this.enableShortKeyActionConfirmBox.setEnabled(Boolean.TRUE);
////            this.enableShortKeyActionConfirmBox.setSelected(Boolean.TRUE);
////            minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.TRUE);
//        } else {
//            this.enableShortKeyAction.setSelected(Boolean.FALSE);
//            this.enableShortKeyActionConfirmBox.setEnabled(Boolean.FALSE);
//            this.enableShortKeyActionConfirmBox.setSelected(Boolean.FALSE);
//            minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.FALSE);
//        }
//
//        this.enableShortKeyAction.addItemListener((ItemEvent e) -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                minifyProperty.setEnableShortKeyAction(Boolean.TRUE);
//                enableShortKeyActionConfirmBox.setEnabled(Boolean.TRUE);
//                enableShortKeyActionConfirmBox.setSelected(Boolean.TRUE);
//                minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.TRUE);
//            } else {
//                minifyProperty.setEnableShortKeyAction(Boolean.FALSE);
//                enableShortKeyActionConfirmBox.setEnabled(Boolean.FALSE);
//                enableShortKeyActionConfirmBox.setSelected(Boolean.FALSE);
//                minifyProperty.setEnableShortKeyActionConfirmBox(Boolean.FALSE);
//            }
//            //minifyPropertyController.writeMinifyProperty(minifyProperty);
//        });
//        this.enableShortKeyActionConfirmBox.setSelected(minifyProperty.isEnableShortKeyActionConfirmBox());
//
//        this.enableShortKeyActionConfirmBox.addItemListener((ItemEvent e) -> {
//            minifyProperty.setEnableShortKeyActionConfirmBox(e.getStateChange() == ItemEvent.SELECTED);
//            //minifyPropertyController.writeMinifyProperty(minifyProperty);
//        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane8 = new javax.swing.JLayeredPane();
        chbAddLogToFile = new javax.swing.JCheckBox();
        chbEnableOutputLogAlert = new javax.swing.JCheckBox();
        jLayeredPane10 = new javax.swing.JLayeredPane();
        chbEnableShortKeyAction = new javax.swing.JCheckBox();
        chbEnableShortKeyActionConfirmBox = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        chbCreateSeparateBuildFolder = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        chbMinifyAllFiles = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        txtExtensionToMinifyRegex = new javax.swing.JTextField();

        jLayeredPane8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.jLayeredPane8.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        chbAddLogToFile.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(chbAddLogToFile, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.chbAddLogToFile.text")); // NOI18N
        chbAddLogToFile.setContentAreaFilled(false);
        jLayeredPane8.add(chbAddLogToFile);
        chbAddLogToFile.setBounds(10, 20, 280, 20);

        chbEnableOutputLogAlert.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(chbEnableOutputLogAlert, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.chbEnableOutputLogAlert.text")); // NOI18N
        chbEnableOutputLogAlert.setContentAreaFilled(false);
        jLayeredPane8.add(chbEnableOutputLogAlert);
        chbEnableOutputLogAlert.setBounds(10, 40, 390, 20);

        jLayeredPane10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.jLayeredPane10.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        chbEnableShortKeyAction.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(chbEnableShortKeyAction, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.chbEnableShortKeyAction.text")); // NOI18N
        chbEnableShortKeyAction.setContentAreaFilled(false);
        jLayeredPane10.add(chbEnableShortKeyAction);
        chbEnableShortKeyAction.setBounds(10, 20, 280, 20);

        chbEnableShortKeyActionConfirmBox.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(chbEnableShortKeyActionConfirmBox, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.chbEnableShortKeyActionConfirmBox.text")); // NOI18N
        chbEnableShortKeyActionConfirmBox.setContentAreaFilled(false);
        chbEnableShortKeyActionConfirmBox.setEnabled(false);
        jLayeredPane10.add(chbEnableShortKeyActionConfirmBox);
        chbEnableShortKeyActionConfirmBox.setBounds(10, 40, 350, 20);

        chbCreateSeparateBuildFolder.setBackground(new java.awt.Color(255, 255, 255));
        org.openide.awt.Mnemonics.setLocalizedText(chbCreateSeparateBuildFolder, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.chbCreateSeparateBuildFolder.text")); // NOI18N
        chbCreateSeparateBuildFolder.setActionCommand(org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.chbCreateSeparateBuildFolder.actionCommand")); // NOI18N
        chbCreateSeparateBuildFolder.setContentAreaFilled(false);

        jLabel6.setForeground(new java.awt.Color(102, 102, 255));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(chbMinifyAllFiles, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.chbMinifyAllFiles.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.jLabel3.text")); // NOI18N

        txtExtensionToMinifyRegex.setText(org.openide.util.NbBundle.getMessage(FolderOptionsPanel.class, "FolderOptionsPanel.txtExtensionToMinifyRegex.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(chbCreateSeparateBuildFolder)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbMinifyAllFiles)
                            .addComponent(txtExtensionToMinifyRegex))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbCreateSeparateBuildFolder)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtExtensionToMinifyRegex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbMinifyAllFiles)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JCheckBox chbAddLogToFile;
    public javax.swing.JCheckBox chbCreateSeparateBuildFolder;
    public javax.swing.JCheckBox chbEnableOutputLogAlert;
    public javax.swing.JCheckBox chbEnableShortKeyAction;
    public javax.swing.JCheckBox chbEnableShortKeyActionConfirmBox;
    public javax.swing.JCheckBox chbMinifyAllFiles;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane10;
    private javax.swing.JLayeredPane jLayeredPane8;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JTextField txtExtensionToMinifyRegex;
    // End of variables declaration//GEN-END:variables
}
