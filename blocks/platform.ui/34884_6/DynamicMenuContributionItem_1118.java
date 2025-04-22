
package org.eclipse.ui.internal.menus;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IConfigurationElement;

public class ControlContributionRegistry {

	private static Map<String, IConfigurationElement> registry = new HashMap<String, IConfigurationElement>();

	public static void clear() {
		registry.clear();
	}

	public static void add(String id, IConfigurationElement element) {
		registry.put(id, element);
	}

	public static IConfigurationElement get(String id) {
		return registry.get(id);
	}

}
