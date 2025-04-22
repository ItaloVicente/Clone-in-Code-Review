package org.eclipse.e4.ui.css.swt.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class EclipsePreferencesHelper {
	private final static String PROPS_OVERRIDDEN_BY_CSS_PROP = "overriddenByCSS";

	private final static String SEPARATOR = ",";

	private final static String MULTI_VALUE_FORMATTER = "%s%s" + SEPARATOR;

	public static void appendOverriddenPropertyName(
			IEclipsePreferences preferences, String name) {
		String value = preferences.get(PROPS_OVERRIDDEN_BY_CSS_PROP, ",");
		if (!value.contains(SEPARATOR + name + SEPARATOR)) {
			preferences.put(PROPS_OVERRIDDEN_BY_CSS_PROP,
					String.format(MULTI_VALUE_FORMATTER, value, name));
		}
	}

	public static List<String> getOverriddenPropertyNames(
			IEclipsePreferences preferences) {
		String value = preferences.get(PROPS_OVERRIDDEN_BY_CSS_PROP, null);
		if (value == null) {
			return Collections.emptyList();
		}
		List<String> result = new ArrayList<String>();
		for (String name : value.split(SEPARATOR)) {
			if (name != null && !name.isEmpty()) {
				result.add(name);
			}
		}
		return result;
	}

	public static void removeOverriddenPropertyNames(
			IEclipsePreferences preferences) {
		preferences.remove(PROPS_OVERRIDDEN_BY_CSS_PROP);
	}
}
