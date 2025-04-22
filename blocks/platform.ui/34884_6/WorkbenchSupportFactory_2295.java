package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.osgi.service.prefs.BackingStoreException;

public class WorkbenchPreferenceInitializer extends
		AbstractPreferenceInitializer {
	
	

	@Override
	public void initializeDefaultPreferences() {
		IScopeContext context = new DefaultScope();
		IEclipsePreferences node = context.getNode(WorkbenchPlugin
				.getDefault().getBundle().getSymbolicName());

		node
				.putBoolean(IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT,
						true);

		node.putBoolean(IPreferenceConstants.EDITORLIST_PULLDOWN_ACTIVE, false);
		node.putBoolean(IPreferenceConstants.EDITORLIST_DISPLAY_FULL_NAME,
				false);
		node.putBoolean(IPreferenceConstants.STICKY_CYCLE, false);
		node.putBoolean(IPreferenceConstants.REUSE_EDITORS_BOOLEAN, false);
		node.putBoolean(IPreferenceConstants.REUSE_DIRTY_EDITORS, true);
		node.putInt(IPreferenceConstants.REUSE_EDITORS, 8);
		node.putBoolean(IPreferenceConstants.OPEN_ON_SINGLE_CLICK, false);
		node.putBoolean(IPreferenceConstants.SELECT_ON_HOVER, false);
		node.putBoolean(IPreferenceConstants.OPEN_AFTER_DELAY, false);
		node.putInt(IPreferenceConstants.RECENT_FILES, 4);

		node.putBoolean(IWorkbenchPreferenceConstants.DISABLE_OPEN_EDITOR_IN_PLACE, false);
		
		node.putInt(IPreferenceConstants.WORKBENCH_SAVE_INTERVAL, 5);

		node.putBoolean(IPreferenceConstants.USE_IPERSISTABLE_EDITORS, true);
		
		node.putBoolean(IPreferenceConstants.COOLBAR_VISIBLE, true);
		node.putBoolean(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE, true);

		node.putInt(IPreferenceConstants.EDITOR_TAB_WIDTH, 3); // high
		node.putInt(IPreferenceConstants.OPEN_VIEW_MODE,
				IPreferenceConstants.OVM_EMBED);
		node.putBoolean(IPreferenceConstants.FVB_HIDE, false);
		node.putInt(IPreferenceConstants.OPEN_PERSP_MODE,
				IPreferenceConstants.OPM_ACTIVE_PAGE);
		node.put(IPreferenceConstants.ENABLED_DECORATORS, ""); //$NON-NLS-1$
		node.putInt(IPreferenceConstants.EDITORLIST_SELECTION_SCOPE,
				IPreferenceConstants.EDITORLIST_SET_PAGE_SCOPE); // Current
		node.putInt(IPreferenceConstants.EDITORLIST_SORT_CRITERIA,
				IPreferenceConstants.EDITORLIST_NAME_SORT); // Name Sort
		node.putBoolean(IPreferenceConstants.COLOR_ICONS, true);
		node.putInt(IPreferenceConstants.KEYS_PREFERENCE_SELECTED_TAB, 0);
		node.putBoolean(IPreferenceConstants.MULTI_KEY_ASSIST, true);
		node.putInt(IPreferenceConstants.MULTI_KEY_ASSIST_TIME, 1000);

		node.putBoolean("ENABLE_CONFIGURABLE_PROJECT_WIZARD", false); //$NON-NLS-1$
		node.putInt("SINGLE_CLICK_METHOD", OpenStrategy.DOUBLE_CLICK); //$NON-NLS-1$
		node.putBoolean("ENABLE_COOL_BARS", true); //$NON-NLS-1$
		node.putBoolean("ENABLE_NEW_MENUS", true); //$NON-NLS-1$   
		node.putBoolean("DISABLE_DIALOG_FONT", false); //$NON-NLS-1$

		node.putBoolean(IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR, false);
		node.putInt(IHeapStatusConstants.PREF_UPDATE_INTERVAL, 500);
		node.putBoolean(IHeapStatusConstants.PREF_SHOW_MAX, false);
		node.putBoolean(IPreferenceConstants.OVERRIDE_PRESENTATION, false);
		
		node.put(IPreferenceConstants.NL_EXTENSIONS, ""); //$NON-NLS-1$
		node.putInt(IPreferenceConstants.LAYOUT_DIRECTION, SWT.NONE);
		node.putBoolean(IPreferenceConstants.BIDI_SUPPORT, false);
		node.put(IPreferenceConstants.TEXT_DIRECTION, ""); //$NON-NLS-1$

		IEclipsePreferences rootNode = (IEclipsePreferences) Platform
				.getPreferencesService().getRootNode()
				.node(InstanceScope.SCOPE);

		final String workbenchName = WorkbenchPlugin.getDefault().getBundle()
				.getSymbolicName();
		try {
			if (rootNode.nodeExists(workbenchName)) {
				((IEclipsePreferences) rootNode.node(workbenchName))
						.addPreferenceChangeListener(PlatformUIPreferenceListener
								.getSingleton());
			}
		} catch (BackingStoreException e) {
			IStatus status = new Status(IStatus.ERROR, WorkbenchPlugin
					.getDefault().getBundle().getSymbolicName(), IStatus.ERROR,
					e.getLocalizedMessage(), e);
			WorkbenchPlugin.getDefault().getLog().log(status);
		}
	
	}

}
