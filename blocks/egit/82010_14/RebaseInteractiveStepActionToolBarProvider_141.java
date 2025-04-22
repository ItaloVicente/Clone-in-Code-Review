package org.eclipse.egit.ui.internal.rebase;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.jface.preference.IPreferenceStore;

public class RebaseInteractivePreferences {

	private static IPreferenceStore getPreferencesStore() {
		return Activator.getDefault().getPreferenceStore();
	}

	public static boolean isOrderReversed() {
		return getPreferencesStore().getBoolean(
				UIPreferences.REBASE_INTERACTIVE_ORDER_REVERSE);
	}

	public static void setOrderReversed(boolean reversed) {
		getPreferencesStore().setValue(
				UIPreferences.REBASE_INTERACTIVE_ORDER_REVERSE, reversed);
	}

	public static boolean isReactOnSelection() {
		return getPreferencesStore().getBoolean(
				UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION);
	}

	public static void setReactOnSelection(boolean react) {
		getPreferencesStore().setValue(
				UIPreferences.REBASE_INTERACTIVE_SYNC_SELECTION, react);
	}
}
