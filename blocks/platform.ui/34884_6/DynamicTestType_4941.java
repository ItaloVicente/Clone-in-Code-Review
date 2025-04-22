package org.eclipse.ui.tests.dynamicplugins;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.tests.leaks.LeakTests;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

public abstract class DynamicTestCase extends UITestCase implements
		IRegistryChangeListener {

	private volatile boolean addedRecieved;

	private Bundle newBundle;

	private volatile boolean removedRecieved;
	
	private WeakReference addedDelta;
	
	private WeakReference removedDelta;
	
	private ReferenceQueue queue;

	public DynamicTestCase(String testName) {
		super(testName);
	}

	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		try {
			removeBundle();
		}
		finally {
			Platform.getExtensionRegistry().removeRegistryChangeListener(this);
			queue = null;
		}
	}

	protected final Bundle getBundle() {
		if (newBundle == null) {
			Platform.getExtensionRegistry().addRegistryChangeListener(this);
			reset();
			queue = new ReferenceQueue();
			try {
				newBundle = DynamicUtils.installPlugin(getInstallLocation());
			} catch (Exception e) {
				fail(e.getMessage());
			}

			long startTime = System.currentTimeMillis();
			long potentialEndTime = startTime + 5000;
			boolean timeToFail = false;
			while (!hasAddedEventPropagated() && !timeToFail) {
				processEvents();
				timeToFail = System.currentTimeMillis() > potentialEndTime;
     			Thread.yield();
			}
			assertTrue("Expected ADDED event did not arrive in time", hasAddedEventPropagated());
			try {
				LeakTests.checkRef(queue, addedDelta);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			processEvents();
			Platform.getExtensionRegistry().removeRegistryChangeListener(this);
		}
		return newBundle;
	}

	protected String getDeclaringNamespace() {
		return WorkbenchPlugin.PI_WORKBENCH;
	}

	protected abstract String getExtensionId();

	protected abstract String getExtensionPoint();

	protected abstract String getInstallLocation();
	
	protected String getMarkerClass() {
		return null;
	}
	
	public void testClass() throws Exception {	
		String className = getMarkerClass();
		if (className == null)
			return;		
		
		setName("testClass() for " + getClass().getName());
		
		Bundle bundle = getBundle();
		
		Class clazz = bundle.loadClass(className);
		assertNotNull(clazz);
		ReferenceQueue myQueue = new ReferenceQueue();
		WeakReference ref = new WeakReference(clazz.getClassLoader(), myQueue);
		clazz = null; //null our refs
		bundle = null;
		removeBundle();
		LeakTests.checkRef(myQueue, ref);
	}

	protected final boolean hasAddedEventPropagated() {
		return addedRecieved;
	}

	protected final boolean hasRemovedEventPropagated() {
		return removedRecieved;
	}

	@Override
	public void registryChanged(IRegistryChangeEvent event) {
		IExtensionDelta delta = event.getExtensionDelta(
				getDeclaringNamespace(), getExtensionPoint(), getExtensionId());
		if (delta != null) {
			if (delta.getKind() == IExtensionDelta.ADDED) {
				addedDelta = new WeakReference(delta, queue);
				setAddedEventPropagated(true);
			}
			else if (delta.getKind() == IExtensionDelta.REMOVED) {
				removedDelta = new WeakReference(delta, queue);
				setRemovedEventPropagated(true);
			}
		}
	}

	protected final void removeBundle() {
		if (newBundle != null) {
			Platform.getExtensionRegistry().addRegistryChangeListener(this);
			queue = new ReferenceQueue();
			try {
				DynamicUtils.uninstallPlugin(newBundle);
				long startTime = System.currentTimeMillis();
				long potentialEndTime = startTime + 5000;
				boolean timeToFail = false;
				while (!hasRemovedEventPropagated() && !timeToFail) {
					processEvents();
					timeToFail = System.currentTimeMillis() > potentialEndTime;
					Thread.yield();
				}
				assertTrue("Expected REMOVED event did not arrive in time", hasRemovedEventPropagated());
				try {
					LeakTests.checkRef(queue, removedDelta);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (BundleException e) {
				fail(e.getMessage());
			} finally {
				newBundle = null;
			}
			processEvents();
			Platform.getExtensionRegistry().removeRegistryChangeListener(this);
		}
	}

	private void reset() {
		addedDelta = null;
		removedDelta = null;
		setAddedEventPropagated(false);
		setRemovedEventPropagated(false);
	}

	protected final void setAddedEventPropagated(boolean added) {
		this.addedRecieved = added;
	}

	protected final void setRemovedEventPropagated(boolean removed) {
		this.removedRecieved = removed;
	}
}
