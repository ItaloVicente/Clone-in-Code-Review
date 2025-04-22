package org.eclipse.ui.dialogs;

import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.dialogs.FilteredPreferenceDialog;
import org.eclipse.ui.internal.dialogs.PropertyDialog;
import org.eclipse.ui.internal.dialogs.PropertyPageContributorManager;
import org.eclipse.ui.internal.dialogs.PropertyPageManager;
import org.eclipse.ui.internal.dialogs.WorkbenchPreferenceDialog;

public final class PreferencesUtil {
	
	public final static int OPTION_NONE = 0;

	public final static int OPTION_FILTER_LOCKED = 1;
	
	private static void applyOptions(Object data, String[] displayedIds,
			FilteredPreferenceDialog dialog, int options) {
		if (data != null) {
			dialog.setPageData(data);
			IPreferencePage page = dialog.getCurrentPage();
			if (page instanceof PreferencePage) {
				((PreferencePage) page).applyData(data);
			}
		}

		if (displayedIds != null) {
			dialog.showOnly(displayedIds);
		}
		
		if ((options & OPTION_FILTER_LOCKED) != 0) {
			dialog.setLocked(true);
		}
	}

	public static final PreferenceDialog createPreferenceDialogOn(Shell shell,
			String preferencePageId, String[] displayedIds, Object data) {
		return createPreferenceDialogOn(shell, preferencePageId, displayedIds, data,
				OPTION_NONE);
	}

	public static final PreferenceDialog createPropertyDialogOn(Shell shell,
			final IAdaptable element, String propertyPageId,
			String[] displayedIds, Object data) {
		return createPropertyDialogOn(shell, element, propertyPageId, displayedIds, data,
				OPTION_NONE);
	}

	public static final PreferenceDialog createPreferenceDialogOn(Shell shell,
			String preferencePageId, String[] displayedIds, Object data, int options) {
		FilteredPreferenceDialog dialog = WorkbenchPreferenceDialog
		.createDialogOn(shell, preferencePageId);
		
		applyOptions(data, displayedIds, dialog, options);
		
		return dialog;
	}

	public static final PreferenceDialog createPropertyDialogOn(Shell shell,
			final IAdaptable element, String propertyPageId,
			String[] displayedIds, Object data, int options) {
		
		FilteredPreferenceDialog dialog = PropertyDialog.createDialogOn(shell,
				propertyPageId, element);
		
		if (dialog == null) {
			return null;
		}
		
		applyOptions(data, displayedIds, dialog, options);
		
		return dialog;
		
	}

	public static final PreferenceDialog createPropertyDialogOn(Shell shell,
			final Object element, String propertyPageId, String[] displayedIds,
			Object data, int options) {
		FilteredPreferenceDialog dialog = PropertyDialog.createDialogOn(shell,
				propertyPageId, element);
		if (dialog == null)
			return null;
		applyOptions(data, displayedIds, dialog, options);
		return dialog;
	}

	public static boolean hasPropertiesContributors(Object element) {
		if (element == null || !(element instanceof IAdaptable))
			return false;
		Collection contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(element);
		return contributors != null && contributors.size() > 0;
	}

	public static IPreferenceNode[] propertiesContributorsFor(Object element) {
		PropertyPageManager pageManager = new PropertyPageManager();
			if (element == null) {
			return null;
		}
		PropertyPageContributorManager.getManager().contribute(pageManager,
				element);
		List pages =  pageManager.getElements(PreferenceManager.PRE_ORDER);
		IPreferenceNode[] nodes = new IPreferenceNode[pages.size()];
		pages.toArray(nodes);
		return nodes;
	}

}
