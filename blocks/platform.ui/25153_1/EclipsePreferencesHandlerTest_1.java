package org.eclipse.e4.ui.css.swt.helpers;

import static org.eclipse.e4.ui.css.swt.helpers.EclipsePreferencesHelper.PROPS_OVERRIDDEN_BY_CSS_PROP;
import static org.eclipse.e4.ui.css.swt.helpers.EclipsePreferencesHelper.SEPARATOR;
import static org.eclipse.e4.ui.css.swt.helpers.EclipsePreferencesHelper.appendOverriddenPropertyName;
import static org.eclipse.e4.ui.css.swt.helpers.EclipsePreferencesHelper.getOverriddenPropertyNames;
import static org.eclipse.e4.ui.css.swt.helpers.EclipsePreferencesHelper.removeOverriddenPropertyNames;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.internal.preferences.EclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class EclipsePreferencesHelperTest extends TestCase {
	public void testAppendOverriddenPropertyName() throws Exception {
		IEclipsePreferences preferences = new EclipsePreferences();

		appendOverriddenPropertyName(preferences, "prop1");
		appendOverriddenPropertyName(preferences, "prop2");
		appendOverriddenPropertyName(preferences, "prop3");

		String overriddenPreferences = preferences.get(
				PROPS_OVERRIDDEN_BY_CSS_PROP, "");

		assertTrue(overriddenPreferences.contains(SEPARATOR + "prop1"
				+ SEPARATOR));
		assertTrue(overriddenPreferences.contains(SEPARATOR + "prop2"
				+ SEPARATOR));
		assertTrue(overriddenPreferences.contains(SEPARATOR + "prop3"
				+ SEPARATOR));
	}

	public void testGetOverriddenPropertyNames() throws Exception {
		IEclipsePreferences preferences = new EclipsePreferences();
		appendOverriddenPropertyName(preferences, "prop1");
		appendOverriddenPropertyName(preferences, "prop2");
		appendOverriddenPropertyName(preferences, "prop3");

		List<String> propertyNames = getOverriddenPropertyNames(preferences);

		assertEquals(3, propertyNames.size());
		assertTrue(propertyNames.add("prop1"));
		assertTrue(propertyNames.add("prop2"));
		assertTrue(propertyNames.add("prop3"));
	}

	public void testRemoveOverriddenPropertyNames() throws Exception {
		IEclipsePreferences preferences = new EclipsePreferences();
		appendOverriddenPropertyName(preferences, "prop1");

		removeOverriddenPropertyNames(preferences);

		assertNull(preferences.get(PROPS_OVERRIDDEN_BY_CSS_PROP, null));
	}
}
