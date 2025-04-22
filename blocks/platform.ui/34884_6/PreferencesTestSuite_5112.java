package org.eclipse.ui.tests.preferences;

import junit.framework.TestCase;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.tests.preferences.SamplePreferencePage;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.FilteredPreferenceDialog;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PreferencesDialogTest extends TestCase {

	static ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.tests",
					"icons/anything.gif");

	public Shell shell;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		shell = new Shell();
	}

	@Override
	protected void tearDown() throws Exception {
		if (shell != null) {
			shell.dispose();
			shell = null;
		}
		super.tearDown();
	}

	public void testCustomManager() {
		PreferenceManager manager = new PreferenceManager();

		IPreferencePage page1 = new SamplePreferencePage("Top", "First Sample");
		IPreferenceNode node1 = new PreferenceNode("Top", page1);
		manager.addToRoot(node1);

		IPreferencePage page2 = new SamplePreferencePage("Sub", "Second Sample");
		IPreferenceNode node2 = new PreferenceNode("Sub", page2);
		node1.add(node2);

		PreferenceDialog dialog = null;
		try {
			dialog = new PreferenceDialog(shell, manager);
			dialog.setBlockOnOpen(false);

			dialog.open();
		} finally {
			if (dialog != null)
				dialog.close();
		}
	}

	public void testMixedNodes() {
		PreferenceManager manager = PlatformUI.getWorkbench().getPreferenceManager();

		IPreferencePage page1 = new SamplePreferencePage("Top", "First Sample");
		IPreferenceNode node1 = new PreferenceNode("Top", page1);
		manager.addToRoot(node1);

		IPreferencePage page2 = new SamplePreferencePage("Sub", "Second Sample");
		IPreferenceNode node2 = new PreferenceNode("Sub", page2);
		manager.addToRoot(node2);
		
		PreferenceDialog dialog = null;
		try {
			dialog = new PreferenceDialog(shell, manager);
			dialog.setBlockOnOpen(false);

			dialog.open();
		} finally {
			if (dialog != null)
				dialog.close();
			manager.remove(node2);
			manager.remove(node1);
		}
	}
	
	public void testWithIcons() {
		PreferenceManager manager = new PreferenceManager();

		IPreferencePage page1 = new SamplePreferencePage("Zzz", "First Sample");
		PreferenceNode node1 = new PreferenceNode("one", "Zzz", descriptor,
				SamplePreferencePage.class.getName());
		node1.setPage(page1);
		manager.addToRoot(node1);

		IPreferencePage page2 = new SamplePreferencePage("Aaa", "Second Sample");
		PreferenceNode node2 = new PreferenceNode("two", "Aaa", descriptor,
				SamplePreferencePage.class.getName());
		node2.setPage(page2);
		manager.addToRoot(node2);

		PreferenceDialog dialog = null;
		try {
			dialog = new PreferenceDialog(shell, manager);
			dialog.setBlockOnOpen(false);

			dialog.open();
		} finally {
			if (dialog != null)
				dialog.close();
		}
	}

	public void testWithSorting() {
		PreferenceManager manager = new PreferenceManager();

		IPreferencePage page1 = new SamplePreferencePage("Zzz", "First Sample");
		IPreferenceNode node1 = new PreferenceNode("abc", page1);
		manager.addToRoot(node1);
		IPreferencePage page2 = new SamplePreferencePage("Aaa", "Second Sample");
		IPreferenceNode node2 = new PreferenceNode("www", page2);
		manager.addToRoot(node2);

		FilteredPreferenceDialog dialog = null;
		try {
			dialog = new FilteredPreferenceDialog(shell, manager) {};
			dialog.setBlockOnOpen(false);

			dialog.open();

			assertEquals(page2, dialog.getCurrentPage());

			TreeItem item = dialog.getTreeViewer().getTree().getItem(0);
			assertEquals("Aaa", item.getText());
		} finally {
			if (dialog != null)
				dialog.close();
		}
	}
}
