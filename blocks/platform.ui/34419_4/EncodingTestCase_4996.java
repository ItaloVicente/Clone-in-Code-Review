
package org.eclipse.ui.tests.dynamicplugins;

import junit.framework.TestSuite;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkingSet;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.intro.IIntroPart;
import org.osgi.framework.Bundle;

public class WorkingSetTests extends DynamicTestCase {

	public static TestSuite suite() {
		TestSuite ts = new TestSuite();
		ts.addTest(new WorkingSetTests("testWorkingSetUpdater"));
		ts.addTest(new WorkingSetTests("testWorkingSetWithBasicElementAdapter"));
		ts.addTest(new WorkingSetTests("testWorkingSetWithCustomElementAdapter"));
		return ts;
	}

	public WorkingSetTests(String testName) {
		super(testName);
	}

	@Override
	protected String getMarkerClass() {
		return "org.eclipse.ui.dynamic.DynamicWorkingSetElementAdapter";
	}

	@Override
	public void testClass() throws Exception {
		super.testClass();
	}

	@Override
	protected String getExtensionId() {
		return "newWorkingSet1.testDynamicWorkingSetAddition1";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_WORKINGSETS;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newWorkingSet1";
	}

	public void testWorkingSetUpdater() throws ClassNotFoundException {
		final PropertyChangeEvent[] events = new PropertyChangeEvent[1];
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
				.getWorkingSetManager();
		WorkingSet workingSet = (WorkingSet) workingSetManager
				.createWorkingSet("dynamicSet3", new IAdaptable[0]);
		workingSet.setId("org.eclipse.newWorkingSet1.WorkingSet1");
		workingSetManager.addWorkingSet(workingSet);

		IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				synchronized (this) {
					events[0] = event;
				}
			}
		};

		workingSetManager.addPropertyChangeListener(propertyChangeListener);
		try {
			getBundle();
			getBundle().loadClass(getMarkerClass()); // ensure the class is
			assertEquals("Bundle not active after classloading", Bundle.ACTIVE,
					getBundle().getState());
			PropertyChangeEvent event = events[0];
			int i = 0;
			while (event == null && i <= 10) {
				try {
					processEvents();
					Thread.sleep(250);
					i++;
				} catch (InterruptedException e) {
				}
				synchronized (propertyChangeListener) {
					event = events[0];
				}
			}
			assertNotNull(event);
			assertEquals(
					IWorkingSetManager.CHANGE_WORKING_SET_UPDATER_INSTALLED,
					event.getProperty());
			assertEquals(null, event.getOldValue());
			assertNotNull(event.getNewValue());
			assertTrue(event.getNewValue().getClass().getName().equals(
					"org.eclipse.ui.dynamic.DynamicWorkingSetUpdater"));
			events[0] = null;
			i = 0;
			removeBundle();
			event = events[0];
			while (event == null && i <= 10) {
				try {
					processEvents();
					Thread.sleep(250);
					i++;
				} catch (InterruptedException e) {
				}
				synchronized (propertyChangeListener) {
					event = events[0];
				}
			}
			assertNotNull(event);
			assertEquals(
					IWorkingSetManager.CHANGE_WORKING_SET_UPDATER_UNINSTALLED,
					event.getProperty());
			assertEquals(null, event.getNewValue());
			assertNotNull(event.getOldValue());
			assertTrue(event.getOldValue().getClass().getName().equals(
					"org.eclipse.ui.dynamic.DynamicWorkingSetUpdater"));
		} finally {
			workingSetManager
					.removePropertyChangeListener(propertyChangeListener);
		}
	}

	public void testWorkingSetWithBasicElementAdapter() {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
				.getWorkingSetManager();
		IAdaptable adaptable = new IAdaptable() {

			@Override
			public Object getAdapter(Class adapter) {
				if (adapter == IResource.class) {
					return ResourcesPlugin.getWorkspace().getRoot();
				}
				return null;
			}
		};
		WorkingSet workingSet = (WorkingSet) workingSetManager
				.createWorkingSet("dynamicSet2", new IAdaptable[0]);
		workingSet.setId("org.eclipse.newWorkingSet1.WorkingSet2");
		workingSetManager.addWorkingSet(workingSet);
		IAdaptable[] elementsToAdapt = new IAdaptable[] { adaptable };
		IAdaptable[] adaptedElements = workingSet
				.adaptElements(elementsToAdapt);
		assertEquals(elementsToAdapt, adaptedElements);
		getBundle();
		assertFalse("Bundle is active too early", Bundle.ACTIVE == getBundle()
				.getState());
		adaptedElements = workingSet.adaptElements(elementsToAdapt);
		assertEquals(1, adaptedElements.length);
		assertEquals(ResourcesPlugin.getWorkspace().getRoot(),
				adaptedElements[0]);
		assertFalse("Bundle is active after adapt",
				Bundle.ACTIVE == getBundle().getState());

		removeBundle();
		adaptedElements = workingSet.adaptElements(elementsToAdapt);
		assertEquals(elementsToAdapt, adaptedElements);
	}

	public void testWorkingSetWithCustomElementAdapter()
			throws ClassNotFoundException {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
				.getWorkingSetManager();
		IAdaptable adaptable = new IAdaptable() {

			@Override
			public Object getAdapter(Class adapter) {
				if (adapter == IResource.class) {
					return ResourcesPlugin.getWorkspace().getRoot();
				}
				return null;
			}
		};
		WorkingSet workingSet = (WorkingSet) workingSetManager
				.createWorkingSet("dynamicSet1", new IAdaptable[0]);
		workingSet.setId("org.eclipse.newWorkingSet1.WorkingSet1");
		workingSetManager.addWorkingSet(workingSet);
		IAdaptable[] elementsToAdapt = new IAdaptable[] { adaptable };
		IAdaptable[] adaptedElements = workingSet
				.adaptElements(elementsToAdapt);
		assertEquals(elementsToAdapt, adaptedElements);
		getBundle();
		assertFalse("Bundle is active too early", Bundle.ACTIVE == getBundle()
				.getState());
		adaptedElements = workingSet.adaptElements(elementsToAdapt);
		assertEquals(elementsToAdapt, adaptedElements);
		getBundle().loadClass(getMarkerClass());
		assertEquals("Bundle not active after classloading", Bundle.ACTIVE,
				getBundle().getState());
		adaptedElements = workingSet.adaptElements(elementsToAdapt);
		assertEquals(1, adaptedElements.length);
		assertTrue(adaptedElements[0] instanceof IIntroPart);

		removeBundle();
		adaptedElements = workingSet.adaptElements(elementsToAdapt);
		assertEquals(elementsToAdapt, adaptedElements);
	}
}
