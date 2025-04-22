
package org.eclipse.e4.ui.workbench.addons.preferencestylingaddon;

import org.eclipse.e4.ui.internal.css.swt.preference.PreferenceNode;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.osgi.service.event.Event;
import org.osgi.service.prefs.BackingStoreException;


public class PreferenceStylingAddon {
	private Map<String, IEclipsePreferences> overriddenPrefNamesToPrefs = new HashMap<String, IEclipsePreferences>();

	@Inject
	@Optional
	private void processCSSThemeChanged(@UIEventTopic(IThemeEngine.Events.THEME_CHANGED) Event event) {
		if (event != null) {
			resetOverriddenPreferences();
			overridePreferences(getThemeEngine(event));
		}
	}

	@PostConstruct
	private void create(Display display) {
		display.addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				resetOverriddenPreferences();
			}
		});
	}

	private void overridePreferences(IThemeEngine themeEngine) {
		for (String nodeId : getPreferenceNodeIds()) {
			PreferenceNode nodeOverridable = new PreferenceNode(nodeId);
			themeEngine.applyStyles(nodeOverridable, false);

			if (!nodeOverridable.getOverriddenPreferences().isEmpty()) {
				overridePreferences(nodeId, nodeOverridable.getOverriddenPreferences());
			}
		}
	}

	private void overridePreferences(String preferenceNodeId,
			Map<String, String> preferencesToOverride) {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(preferenceNodeId);
		for (Map.Entry<String, String> entry : preferencesToOverride.entrySet()) {
			String existingValue = preferences.get(entry.getKey(), null);

			if (existingValue == null || existingValue.equals(entry.getValue())) {
				overriddenPrefNamesToPrefs.put(entry.getKey(), preferences);
				preferences.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private void resetOverriddenPreferences() {
		for (Map.Entry<String, IEclipsePreferences> entry : overriddenPrefNamesToPrefs.entrySet()) {
			entry.getValue().remove(entry.getKey());
		}
		overriddenPrefNamesToPrefs.clear();
	}

	private String[] getPreferenceNodeIds() {
		try {
			return InstanceScope.INSTANCE.getNode("").childrenNames(); //$NON-NLS-1$
		} catch (BackingStoreException exc) {
			return new String[0];
		}
	}

	private IThemeEngine getThemeEngine(Event event) {
		return (IThemeEngine) event.getProperty(IThemeEngine.Events.THEME_ENGINE);
	}
}
