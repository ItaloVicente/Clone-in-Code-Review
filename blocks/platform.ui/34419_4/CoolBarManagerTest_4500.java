package org.eclipse.jface.tests.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ContributionItemTest extends JFaceActionTest {

    public ContributionItemTest(String name) {
        super(name);
    }

    public void testParentLink() {
        IContributionManager mgr = new DummyContributionManager();
        ContributionItem item = new ActionContributionItem(new DummyAction());
        assertNull(item.getParent());
        mgr.add(item);
        assertEquals(mgr, item.getParent());
        mgr.remove(item);
        assertNull(item.getParent());
    }

    public void testForceModeText() {
    	Action action = new DummyAction();
    	action.setImageDescriptor(
    	AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.tests",
    			"icons/anything.gif"));
    	ActionContributionItem item = new ActionContributionItem(action);
    	item.fill(getShell());

    	Control[] children = getShell().getChildren();
    	Button button = (Button) children[0];
    	assertEquals("", button.getText());
    	action.setText("Text");
    	assertEquals("", button.getText());
    	item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
    	assertEquals("Text", button.getText());
    	action.setText(null);
    	assertEquals("", button.getText());
    }
}
