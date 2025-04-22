package org.eclipse.ui.internal.preferences;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.PreferenceTransferRegistryReader;
import org.eclipse.ui.internal.registry.RegistryReader;
import org.eclipse.ui.model.WorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class PreferenceTransferElement extends WorkbenchAdapter implements
		IPluginContribution {
	private String id;

	private ImageDescriptor imageDescriptor;

	private IConfigurationElement configurationElement;

	private IPreferenceFilter filter;

	public PreferenceTransferElement(IConfigurationElement configurationElement) {
		this.configurationElement = configurationElement;
		id = configurationElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
	}

	public IConfigurationElement getConfigurationElement() {
		return configurationElement;
	}

	public IPreferenceFilter getFilter() throws CoreException {
		if (filter == null) {
			IConfigurationElement[] mappingConfigurations = PreferenceTransferRegistryReader
					.getMappings(configurationElement);
			int size = mappingConfigurations.length;
			Set scopes = new HashSet(size);
			Map mappingsMap = new HashMap(size);
			for (int i = 0; i < size; i++) {
				String scope = PreferenceTransferRegistryReader
						.getScope(mappingConfigurations[i]);
				scopes.add(scope);

				Map mappings;
				if (!mappingsMap.containsKey(scope)) {
					mappings = new HashMap(size);
					mappingsMap.put(scope, mappings);
				} else {
					mappings = (Map) mappingsMap.get(scope);
					if (mappings == null) {
						continue;
					}
				}

				Map entries = PreferenceTransferRegistryReader
						.getEntry(mappingConfigurations[i]);
				if (entries == null) {
					mappingsMap.put(scope, null);
				} else {
					mappings.putAll(entries);
				}
			}
			filter = new PreferenceFilter((String[]) scopes
					.toArray(new String[scopes.size()]), mappingsMap);
		}
		return filter;
	}

	public String getDescription() {
		return RegistryReader.getDescription(configurationElement);
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return configurationElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
	}

	@Override
	public String getLocalId() {
		return getID();
	}

	@Override
	public String getPluginId() {
		return (configurationElement != null) ? configurationElement
				.getContributor().getName() : null;
	}

	class PreferenceFilter implements IPreferenceFilter {

		private String[] scopes;
		private Map mappings;

		public PreferenceFilter(String[] scopes, Map mappings) {
			this.scopes = scopes;
			this.mappings = mappings;
		}

		@Override
		public String[] getScopes() {
			return scopes;
		}

		@Override
		public Map getMapping(String scope) {
			return (Map) mappings.get(scope);
		}

	}

	@Override
	public String getLabel(Object object) {
		return getName();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		if (imageDescriptor == null) {
			String iconName = configurationElement
					.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
			if (iconName == null) {
				return null;
			}
			imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
					getPluginId(), iconName);
		}
		return imageDescriptor;

	}
}
