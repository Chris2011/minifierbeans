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
package org.netbeans.util.source.minify;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import org.mozilla.javascript.EvaluatorException;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.minify.ui.MinifyProperty;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;
import org.openide.util.actions.CookieAction;

@ActionID(category = "Build",
        id = "org.netbeans.util.source.minify.JSONMinifyClipboard")
@ActionRegistration(displayName = "#CTL_JSONMinifyClipboard")
@ActionReferences({
    @ActionReference(path = "Editors/text/x-json/Popup", position = 400, separatorBefore = 350, separatorAfter = 450)
})
@NbBundle.Messages("CTL_JSONMinifyClipboard=Copy as Minified JSON")

public final class JSONMinifyClipboard extends CookieAction {

    private final static RequestProcessor RP = new RequestProcessor("JSONMinifyClipboard", 1, true);

    @Override
    protected final void performAction(final Node[] activatedNodes) {
        jsonMinify(activatedNodes);
    }

    protected final void jsonMinify(final Node[] activatedNodes) {
        final EditorCookie editorCookie
                = Utilities.actionsGlobalContext().lookup(EditorCookie.class);

        for (final JEditorPane pane : editorCookie.getOpenedPanes()) {
            if (pane.isShowing()
                    && pane.getSelectionEnd() > pane.getSelectionStart()) {
                try {
                    StringSelection content = new StringSelection(selectedSourceAsMinify(pane));
                    Toolkit.getDefaultToolkit().getSystemClipboard().
                            setContents(content, content);
                    return;
                } catch (final Throwable e) {
                    org.openide.ErrorManager.getDefault().notify(e);
                }
            }
        }
    }

    private String selectedSourceAsMinify(final JEditorPane pane) {
        MinifyProperty minifyProperty = MinifyProperty.getInstance();
        StringWriter out = new StringWriter();
        try {
            final TokenSequence ts = TokenHierarchy.get(pane.getDocument()).tokenSequence();
            final StringBuilder sb = new StringBuilder();
            ts.move(pane.getSelectionStart());
            while (ts.moveNext() && ts.offset() < pane.getSelectionEnd()) {
                sb.append(ts.token().text().toString());
            }
            MinifyUtil minifyUtil = new MinifyUtil();
            minifyUtil.compressJsonInternal(new StringReader(sb.toString()), out, minifyProperty);
            JOptionPane.showMessageDialog(null, "Copied as minified JSON Source", "Copied", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (EvaluatorException ex) {
            JOptionPane.showMessageDialog(null, "Invalid JSON Source Selected \n " + ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
        return out.toString();
    }

    @Override
    protected final int mode() {
        return CookieAction.MODE_EXACTLY_ONE;
    }

    @Override
    public final String getName() {
        return NbBundle.getMessage(JSMinifyClipboard.class, "CTL_JSONMinifyClipboard");
    }

    @Override
    protected final Class[] cookieClasses() {
        return new Class[]{
            EditorCookie.class
        };
    }

    @Override
    protected final void initialize() {
        super.initialize();
        putValue("noIconInMenu", Boolean.TRUE);
    }

    @Override
    public final HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected final boolean asynchronous() {
        return false;
    }
}
