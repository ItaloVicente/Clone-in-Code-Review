
package org.eclipse.ui.tests.menus;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.PopupMenuExtender;
import org.eclipse.ui.tests.api.ListElement;
import org.eclipse.ui.tests.api.ListView;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug264804Test extends UITestCase {

	public Bug264804Test(String testName) {
		super(testName);
	}

	public void testPopup() throws Exception {
		IWorkbenchWindow window = openTestWindow();

		ListView part = (ListView) window.getActivePage().showView(
				"org.eclipse.ui.tests.api.IActionFilterTest1");
		ListElement red = new ListElement("red");
		ListElement blue = new ListElement("blue");
		ListElement redTrue = new ListElement("red", true);
		part.addElement(red);
		part.addElement(blue);
		part.addElement(redTrue);

		assertNotNull(part);
		MenuManager manager = new MenuManager();

		final ISelectionProvider prov = part.getSelectionProvider();
		prov.setSelection(new StructuredSelection(blue));
		PopupMenuExtender popupMenuExtender = null;
		try {
			popupMenuExtender = new PopupMenuExtender(
					"org.eclipse.ui.tests.Bug264804", manager, prov, part,
					((PartSite)part.getSite()).getContext(), false);

			Menu contextMenu = manager.createContextMenu(window.getShell());
			Event e = new Event();
			e.widget = contextMenu;
			processEvents();
			contextMenu.notifyListeners(SWT.Show, e);

			find("org.eclipse.ui.file.close", manager.getItems());

			contextMenu.notifyListeners(SWT.Hide, e);
			contextMenu.notifyListeners(SWT.Show, e);
			processEvents();

			find("org.eclipse.ui.file.close", manager.getItems());
		} finally {
			popupMenuExtender.dispose();
		}
	}

	private void find(String id, IContributionItem[] items) throws Exception {
		for (IContributionItem item : items) {
			if (id.equals(item.getId())) {
				assertTrue("Should be visible", item.isVisible());
				return;
			}
		}
		fail("Could not find " + id);
	}

}
