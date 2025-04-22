package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.NodeChangeEvent;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;
import org.osgi.service.prefs.BackingStoreException;

public class UIPreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {

		
		IScopeContext context = new DefaultScope();
		IEclipsePreferences node = context.getNode(UIPlugin.getDefault()
				.getBundle().getSymbolicName());
		node.put(IWorkbenchPreferenceConstants.OPEN_NEW_PERSPECTIVE,
				IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE);

		node.put(IWorkbenchPreferenceConstants.PROJECT_OPEN_NEW_PERSPECTIVE,
				IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE);
		node.put(IWorkbenchPreferenceConstants.SHIFT_OPEN_NEW_PERSPECTIVE,
				IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE);
		node.put(IWorkbenchPreferenceConstants.ALTERNATE_OPEN_NEW_PERSPECTIVE,
				IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE);

		node.putBoolean(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR,
				false);

		node
				.putBoolean(
						IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS,
						true);
		node.putBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS, false);
		node.putBoolean(IWorkbenchPreferenceConstants.USE_COLORED_LABELS, true);
		node.put(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,
				IWorkbenchPreferenceConstants.TOP_LEFT);
		node.putBoolean(
				IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR,
				true);
		node.putBoolean(
				IWorkbenchPreferenceConstants.SHOW_OTHER_IN_PERSPECTIVE_MENU,
				true);
		node.putBoolean(
				IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR,
				true);

		node.put(IWorkbenchPreferenceConstants.INITIAL_FAST_VIEW_BAR_LOCATION,
				IWorkbenchPreferenceConstants.BOTTOM);

		node.putBoolean(IWorkbenchPreferenceConstants.SHOW_INTRO, true);

		node.put(IWorkbenchPreferenceConstants.KEY_CONFIGURATION_ID,
				IBindingService.DEFAULT_DEFAULT_ACTIVE_SCHEME_ID);

		node.putBoolean(IWorkbenchPreferenceConstants.SHOW_SYSTEM_JOBS, false);

		node
				.putInt(
						IWorkbenchPreferenceConstants.EDITOR_MINIMUM_CHARACTERS,
						-1);

		node.putInt(IWorkbenchPreferenceConstants.VIEW_MINIMUM_CHARACTERS, 1);

		node.putBoolean(IWorkbenchPreferenceConstants.CLOSE_EDITORS_ON_EXIT,
				false);

		node
				.putBoolean(
						IWorkbenchPreferenceConstants.USE_WINDOW_WORKING_SET_BY_DEFAULT,
						false);

		node
				.putBoolean(IWorkbenchPreferenceConstants.SHOW_FILTERED_TEXTS,
						true);

		node.putBoolean(IWorkbenchPreferenceConstants.ENABLE_DETACHED_VIEWS,
				true);

		node.putBoolean(
				IWorkbenchPreferenceConstants.PROMPT_WHEN_SAVEABLE_STILL_OPEN,
				true);

		node.putBoolean(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, true);

		node.putBoolean(IWorkbenchPreferenceConstants.DISABLE_NEW_FAST_VIEW,
				false);

		node.putBoolean(
				IWorkbenchPreferenceConstants.ENABLE_32_STICKY_CLOSE_BEHAVIOR,
				false);

		node.putInt(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION, SWT.TOP);
		node.putInt(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION, SWT.TOP);
		node.putBoolean(
				IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS, true);
		
		node.putInt(IWorkbenchPreferenceConstants.RECENTLY_USED_WORKINGSETS_SIZE, 5);

		migrateInternalPreferences();

		IEclipsePreferences rootNode = (IEclipsePreferences) Platform
				.getPreferencesService().getRootNode()
				.node(InstanceScope.SCOPE);

		final String uiName = UIPlugin.getDefault().getBundle()
				.getSymbolicName();
		try {
			if (rootNode.nodeExists(uiName)) {
				((IEclipsePreferences) rootNode.node(uiName))
						.addPreferenceChangeListener(PlatformUIPreferenceListener
								.getSingleton());
			}
		} catch (BackingStoreException e) {
			IStatus status = new Status(IStatus.ERROR, UIPlugin.getDefault()
					.getBundle().getSymbolicName(), IStatus.ERROR, e
					.getLocalizedMessage(), e);
			UIPlugin.getDefault().getLog().log(status);
		}

		rootNode
				.addNodeChangeListener(new IEclipsePreferences.INodeChangeListener() {
					public void added(NodeChangeEvent event) {
						if (!event.getChild().name().equals(uiName)) {
							return;
						}
						((IEclipsePreferences) event.getChild())
								.addPreferenceChangeListener(PlatformUIPreferenceListener
										.getSingleton());

					}

					public void removed(NodeChangeEvent event) {

					}

				});
	}

	private void migrateInternalPreferences() {

		IPreferenceStore internalStore = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		IPreferenceStore apiStore = PlatformUI.getPreferenceStore();
		if (internalStore
				.contains(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION)) {
			apiStore.setValue(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION,
					internalStore.getInt(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION));
			internalStore
				.setToDefault(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION);
		}		

		if (internalStore
				.contains(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION)) {
				
			apiStore.setValue(
					IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION,
					internalStore.getInt(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION));
			internalStore
				.setToDefault(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION);
		}


		if (internalStore
				.contains(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS)) {
			apiStore
					.setValue(
							IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS,
							internalStore
							.getBoolean(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS));
			internalStore
					.setToDefault(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS);
		}
	}

}
