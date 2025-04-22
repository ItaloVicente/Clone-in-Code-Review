
package org.eclipse.e4.ui.internal.css.swt.preference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PreferenceNode {
	private String id;

	private Map<String, String> overriddenPreferences;

	public PreferenceNode(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void overridePreference(String name, String value) {
		if (overriddenPreferences == null) {
			overriddenPreferences = new HashMap<String, String>();
		}
		overriddenPreferences.put(name, value);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getOverriddenPreferences() {
		return overriddenPreferences == null ? Collections.EMPTY_MAP : overriddenPreferences;
	}
}
