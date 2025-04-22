
package org.eclipse.ui.internal.preferences;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.RegistryReader;

public class SettingsTransferRegistryReader extends RegistryReader {

	Collection settingsTransfers = new ArrayList();

	public SettingsTransferRegistryReader() {

	}

	public IConfigurationElement[] getSettingTransfers() {

		settingsTransfers = new ArrayList();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		readRegistry(registry, WorkbenchPlugin.PI_WORKBENCH,
				IWorkbenchRegistryConstants.PL_PREFERENCE_TRANSFER);

		IConfigurationElement[] transfers = new IConfigurationElement[settingsTransfers
				.size()];
		settingsTransfers.toArray(transfers);
		return transfers;

	}

	@Override
	protected boolean readElement(IConfigurationElement element) {
		if (element.getName().equals(
				IWorkbenchRegistryConstants.TAG_SETTINGS_TRANSFER)) {

			settingsTransfers.add(element);
			return true;
		}

		return element.getName().equals(
				IWorkbenchRegistryConstants.TAG_TRANSFER);
	}

}
