package org.eclipse.ui.tests.dynamicplugins;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.osgi.framework.Bundle;

public class StartupTests extends DynamicTestCase {
	public StartupTests(String testName) {
		super(testName);
	}

	public void testStartupRun() throws ClassNotFoundException,
			SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Bundle bundle = getBundle();
		Class clazz = bundle.loadClass(getMarkerClass());
		assertNotNull(clazz);
		Field field = clazz.getDeclaredField("history");
		assertNotNull(field);
		assertTrue((field.getModifiers() & Modifier.STATIC) != 0);
		assertNotNull(field.get(null));
	}

	@Override
	protected String getExtensionId() {
		return "newStartup1.testDynamicStartupAddition";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_STARTUP;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newStartup1";
	}

	@Override
	protected String getMarkerClass() {
		return "org.eclipse.ui.dynamic.DynamicStartup";
	}
}
