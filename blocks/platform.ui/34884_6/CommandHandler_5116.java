package org.eclipse.ui.tests.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.WorkingCopyManager;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.osgi.service.prefs.BackingStoreException;

public class WorkingCopyPreferencesTestCase extends UITestCase {

	public WorkingCopyPreferencesTestCase(String name) {
		super(name);
	}

	public void testRemoveKey() {

		String key = "key";
		String value = "value";
		IEclipsePreferences eNode = new InstanceScope().getNode("working.copy.tests.testRemoveKey");
		eNode.put(key, value);
		assertEquals("1.0", value, eNode.get(key, null));

		WorkingCopyManager manager = new WorkingCopyManager();
		IEclipsePreferences prefs = manager.getWorkingCopy(eNode);
		prefs.remove(key);

		try {
			manager.applyChanges();
		} catch (BackingStoreException e) {
			fail("2.99", e);
		}

		assertNull("3.0", eNode.get(key, null));
	}

	public void testRemoveNode() {
		String key = "key";
		String value = "value";
		IEclipsePreferences eNode = new InstanceScope().getNode("working.copy.tests.testRemoveKey");
		eNode.put(key, value);
		assertEquals("1.0", value, eNode.get(key, null));

		WorkingCopyManager manager = new WorkingCopyManager();
		IEclipsePreferences prefs = manager.getWorkingCopy(eNode);
		
		try {
			prefs.removeNode();
		} catch (BackingStoreException e) {
			fail("2.99", e);
		}

		try {
			manager.applyChanges();
		} catch (BackingStoreException e) {
			fail("3.99", e);
		}
	}
}
