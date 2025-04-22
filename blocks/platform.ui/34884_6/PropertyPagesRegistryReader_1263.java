
package org.eclipse.ui.internal.registry;

import com.ibm.icu.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.PreferenceFilterEntry;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.preferences.PreferenceTransferElement;

public class PreferenceTransferRegistryReader extends RegistryReader {
	private List preferenceTransfers;

	private String pluginPoint;

	public PreferenceTransferRegistryReader(String pluginPointId) {
		pluginPoint = pluginPointId;
	}

	protected PreferenceTransferElement createPreferenceTransferElement(
			IConfigurationElement element) {
		if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
			return null;
		}

		if (element.getChildren(IWorkbenchRegistryConstants.TAG_MAPPING) == null) {
			logMissingElement(element, IWorkbenchRegistryConstants.TAG_MAPPING);
			return null;
		}

		return new PreferenceTransferElement(element);
	}

	public PreferenceTransferElement[] getPreferenceTransfers() {
		readPreferenceTransfers();
		PreferenceTransferElement[] transfers = new PreferenceTransferElement[preferenceTransfers
				.size()];
		Collections.sort(preferenceTransfers, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String name1 = ((PreferenceTransferElement) o1).getName();
				String name2 = ((PreferenceTransferElement) o2).getName();

				return Collator.getInstance().compare(name1, name2);
			}
		});
		preferenceTransfers.toArray(transfers);
		return transfers;
	}

	@Override
	protected boolean readElement(IConfigurationElement element) {
		if (element.getName().equals(IWorkbenchRegistryConstants.TAG_TRANSFER)) {

			PreferenceTransferElement transfer = createPreferenceTransferElement(element);
			if (transfer != null)
				preferenceTransfers.add(transfer);
			return true;
		}


		return element.getName().equals(
				IWorkbenchRegistryConstants.TAG_SETTINGS_TRANSFER);
	}

	protected void readPreferenceTransfers() {
		preferenceTransfers = new ArrayList();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		readRegistry(registry, WorkbenchPlugin.PI_WORKBENCH, pluginPoint);
	}

	public static IConfigurationElement[] getMappings(
			IConfigurationElement configElement) {
		IConfigurationElement[] children = configElement
				.getChildren(IWorkbenchRegistryConstants.TAG_MAPPING);
		if (children.length < 1) {
			logMissingElement(configElement,
					IWorkbenchRegistryConstants.TAG_MAPPING);
			return new IConfigurationElement[0];
		}
		return children;
	}

	public static String getScope(IConfigurationElement element) {
		return element.getAttribute(IWorkbenchRegistryConstants.ATT_SCOPE);
	}

	public static Map getEntry(IConfigurationElement element) {
		IConfigurationElement[] entries = element
				.getChildren(IWorkbenchRegistryConstants.TAG_ENTRY);
		if (entries.length == 0) {
			return null;
		}
		Map map = new HashMap(entries.length);
		for (int i = 0; i < entries.length; i++) {
			IConfigurationElement entry = entries[i];
			IConfigurationElement[] keys = entry
					.getChildren(IWorkbenchRegistryConstants.ATT_KEY);
			PreferenceFilterEntry[] prefFilters = null;
			if (keys.length > 0) {
				prefFilters = new PreferenceFilterEntry[keys.length];
				for (int j = 0; j < keys.length; j++) {
					IConfigurationElement keyElement = keys[j];
					prefFilters[j] = new PreferenceFilterEntry(keyElement
									.getAttribute(IWorkbenchRegistryConstants.ATT_NAME),
							keyElement
									.getAttribute(IWorkbenchRegistryConstants.ATT_MATCH_TYPE));
				}
			}
			map.put(entry.getAttribute(IWorkbenchRegistryConstants.ATT_NODE),
					prefFilters);
		}
		return map;
	}
}
