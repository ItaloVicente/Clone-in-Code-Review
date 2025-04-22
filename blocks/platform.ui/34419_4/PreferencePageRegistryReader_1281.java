
package org.eclipse.ui.internal.registry;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;

public final class PreferencePageParameterValues implements IParameterValues {

	public PreferencePageParameterValues() {
		Platform.getExtensionRegistry().addRegistryChangeListener(
				new IRegistryChangeListener() {

					@Override
					public void registryChanged(IRegistryChangeEvent event) {
						if (event.getExtensionDeltas(PlatformUI.PLUGIN_ID,
								IWorkbenchRegistryConstants.PL_PREFERENCES).length > 0) {
							preferenceMap = null;
						}
					}
				});
	}

	private Map preferenceMap;

	private final void collectParameterValues(final Map values,
			final IPreferenceNode[] preferenceNodes, final String namePrefix) {

		for (int i = 0; i < preferenceNodes.length; i++) {
			final IPreferenceNode preferenceNode = preferenceNodes[i];

			final String name;
			if (namePrefix == null) {
				name = preferenceNode.getLabelText();
			} else {
				name = namePrefix
						+ WorkbenchMessages.PreferencePageParameterValues_pageLabelSeparator
						+ preferenceNode.getLabelText();
			}
			final String value = preferenceNode.getId();
			values.put(name, value);

			collectParameterValues(values, preferenceNode.getSubNodes(), name);
		}
	}

	@Override
	public final Map getParameterValues() {
		if (preferenceMap == null) {
			preferenceMap = new TreeMap();

			final PreferenceManager preferenceManager = PlatformUI
					.getWorkbench().getPreferenceManager();
			collectParameterValues(preferenceMap, preferenceManager
					.getRootSubNodes(), null);
		}

		return preferenceMap;
	}

}
