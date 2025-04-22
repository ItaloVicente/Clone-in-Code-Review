package org.eclipse.ui.tests.preferences;

import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.dialogs.AutoSavePreferencePage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AutoSavePreferencePageTest {

	static PreferenceDialog dialog;
	@BeforeClass
	public static void setUpClass() {
		setupPreferenceManager();
		dialog = setupDialog();
		dialog.open();
	}

	@AfterClass
	public static void tearDownClass() {
		dialog.close();
		tearDownPreferenceManager();
	}

	@After
	public void restoreAndCheckDefaults() {
		MyAutoSavePreferencePage page = (MyAutoSavePreferencePage) dialog.getSelectedPage();
		page.performDefaults();
		page.performOk();
		boolean autoSave = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY);
		Assert.assertFalse(autoSave);
		Assert.assertFalse(page.isAutoSaveButtonSelected());
		int autoSaveInterval = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL);
		Assert.assertEquals(60, autoSaveInterval);
		Assert.assertEquals(60, page.getAutoSaveIntervalTextValue());
	}

	@Test
	public void testEnableAutoSave() {
		MyAutoSavePreferencePage page = (MyAutoSavePreferencePage) dialog.getSelectedPage();
		page.selectAutoSaveButton(true);
		boolean autoSave = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY);
		Assert.assertTrue(autoSave);
		Assert.assertTrue(page.isAutoSaveButtonSelected());
		page.performOk();
	}

	@Test
	public void testDisableAutoSave() {
		MyAutoSavePreferencePage page = (MyAutoSavePreferencePage) dialog.getSelectedPage();
		page.selectAutoSaveButton(false);
		boolean autoSave = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY);
		Assert.assertFalse(autoSave);
		Assert.assertFalse(page.isAutoSaveButtonSelected());
		page.performOk();
	}

	@Test
	public void testUpdateAutoSaveInterval() {
		MyAutoSavePreferencePage page = (MyAutoSavePreferencePage) dialog.getSelectedPage();
		page.selectAutoSaveButton(true);
		page.setAutoSaveIntervalTextValue(30);
		int autoSaveInterval = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL);
		Assert.assertEquals(60, autoSaveInterval);
		Assert.assertEquals(30, page.getAutoSaveIntervalTextValue());
		page.performOk();
		autoSaveInterval = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL);
		Assert.assertEquals(30, autoSaveInterval);
	}

	@Test
	public void testUpdateAutoSaveIntervalWithOutOfScopeValue() {
		MyAutoSavePreferencePage page = (MyAutoSavePreferencePage) dialog.getSelectedPage();
		page.selectAutoSaveButton(true);
		page.setAutoSaveIntervalTextValue(0);
		int autoSaveInterval = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL);
		Assert.assertEquals(60, autoSaveInterval);
		Assert.assertEquals(0, page.getAutoSaveIntervalTextValue());
		Assert.assertFalse(page.isValid());
		page.performOk();
		autoSaveInterval = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL);
		Assert.assertEquals(60, autoSaveInterval);
	}

	@Test
	public void testCheckDefaultAutoSaveConfiguration() {
		boolean autoSave = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.SAVE_AUTOMATICALLY);
		Assert.assertFalse(autoSave);
		int autoSaveInterval = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getInt(IPreferenceConstants.SAVE_AUTOMATICALLY_INTERVAL);
		Assert.assertEquals(60, autoSaveInterval);
	}

	private static void setupPreferenceManager() {
		PreferenceManager manager = PlatformUI.getWorkbench().getPreferenceManager();

		IPreferencePage page = new MyAutoSavePreferencePage();
		PreferenceNode node = new PreferenceNode("org.eclipse.ui.preferencePages.AutoSaveTest", "Auto-save", null,
				MyAutoSavePreferencePage.class.getName());
		node.setPage(page);
		manager.addToRoot(node);
	}

	private static void tearDownPreferenceManager() {
		PreferenceManager manager = PlatformUI.getWorkbench().getPreferenceManager();

		manager.remove("org.eclipse.ui.preferencePages.AutoSaveTest");
	}

	private static PreferenceDialog setupDialog() {
		PreferenceDialog dial = new PreferenceDialog(Display.getCurrent().getActiveShell(),
				PlatformUI.getWorkbench().getPreferenceManager());
		dial.setSelectedNode("org.eclipse.ui.preferencePages.AutoSaveTest");
		dial.setBlockOnOpen(false);

		return dial;
	}

	private static class MyAutoSavePreferencePage extends AutoSavePreferencePage {

		@Override
		public boolean isAutoSaveButtonSelected() {
			return super.isAutoSaveButtonSelected();
		}

		@Override
		public void selectAutoSaveButton(boolean enable) {
			super.selectAutoSaveButton(enable);
		}

		@Override
		public int getAutoSaveIntervalTextValue() {
			return super.getAutoSaveIntervalTextValue();
		}

		@Override
		public void setAutoSaveIntervalTextValue(int interval) {
			super.setAutoSaveIntervalTextValue(interval);
		}

	}
}
