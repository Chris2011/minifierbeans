package io.github.chris2011.netbeans.minifierbeans.ui.options;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import org.netbeans.spi.options.AdvancedOption;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

@OptionsPanelController.SubRegistration(
    id = MinificationOptionsPanelController.ID,
    displayName = "#LBL_OptionsPanelName",
    location = "Html5",
    keywords = "#Keywords_MinificationOptions",
    keywordsCategory = "Html5/Minification"
)
public class MinificationOptionsPanelController extends OptionsPanelController {
    public static final String ID = "Minification"; //NOI18N
    private MinificationOptionsPanel panel;
    private boolean initialized = false;
    private final Map<String, OptionsPanelController> categoryToController = new HashMap<>();

    private void init(Lookup masterLookup) {
        if (initialized) {
            return;
        }
        initialized = true;
        panel = new MinificationOptionsPanel();

        Lookup lookup = Lookups.forPath("MinificationOptionsDialog"); // NOI18N
        Iterator<? extends AdvancedOption> it = lookup.lookup(new Lookup.Template<>(AdvancedOption.class)).
                allInstances().iterator();
        while (it.hasNext()) {
            AdvancedOption option = it.next();
            registerOption(option, masterLookup);
        }
    }

    private void registerOption(AdvancedOption option, Lookup masterLookup) {
        String category = option.getDisplayName();
        OptionsPanelController controller = option.create();
        synchronized (categoryToController) {
            categoryToController.put(category, controller);
        }

        panel.addPanel(category, controller.getComponent(masterLookup));

        if ("io.github.chris2011.netbeans.minifierbeans.css.ui.options.CssAdvancedOption".equals(option.getClass().getName())) {
            panel.addPanel(category, controller.getComponent(masterLookup));
        }
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        init(masterLookup);
        return panel;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
    }

    @Override
    public void update() {
        for (OptionsPanelController c : getControllers()) {
            c.update();
        }
    }

    @Override
    public void applyChanges() {
        for (OptionsPanelController c : getControllers()) {
            c.applyChanges();
        }
    }

    @Override
    public void cancel() {
        for (OptionsPanelController c : getControllers()) {
            c.cancel();
        }
    }

    @Override
    public boolean isValid() {
        for (OptionsPanelController c : getControllers()) {
            if (!c.isValid()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isChanged() {
        for (OptionsPanelController c : getControllers()) {
            if (c.isChanged()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return null; // new HelpCtx("...ID") if you have a help set
    }

    @Override
    public void handleSuccessfulSearch(String searchText, List<String> matchedKeywords) {
        Map<String, OptionsPanelController> m;
        synchronized (categoryToController) {
            m = new HashMap<>(categoryToController);
        }
        for (Map.Entry<String, OptionsPanelController> e : m.entrySet()) {
            OptionsPanelController c = e.getValue();
            if (c instanceof MinificationOptionsKeywordsProvider) {
                if (((MinificationOptionsKeywordsProvider) c).acceptKeywords(matchedKeywords)) {
                    panel.selectCategory(e.getKey());
                    break;
                }
            }
        }
    }

    @Override
    protected void setCurrentSubcategory(String subpath) {
        Map<String, OptionsPanelController> m;
        synchronized (categoryToController) {
            m = new HashMap<>(categoryToController);
        }
        for (Map.Entry<String, OptionsPanelController> e : m.entrySet()) {
            if (e.getKey().equals(subpath)) {
                panel.selectCategory(e.getKey());
                break;
            }
        }
    }

    private OptionsPanelController[] getControllers() {
        synchronized (categoryToController) {
            return categoryToController.values().toArray(new OptionsPanelController[categoryToController.values().size()]);
        }
    }
}
