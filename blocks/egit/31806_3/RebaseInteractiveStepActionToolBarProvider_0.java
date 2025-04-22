package org.eclipse.egit.ui.internal.rebase;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;

public class RebaseInteractivePreferences {
	public static boolean isOrderReversed() {
		return Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.REBASE_INTERACTIVE_ORDER_INVERSE);
	}

	public static void setOrderReversed(boolean reversed) {
		Activator
				.getDefault()
				.getPreferenceStore()
				.setValue(UIPreferences.REBASE_INTERACTIVE_ORDER_INVERSE,
						reversed);
	}
}
