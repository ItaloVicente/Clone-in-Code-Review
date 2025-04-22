package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.preferences.WorkbenchPreferenceExpressionNode;


public class PropertyPageManager extends PreferenceManager {
	public PropertyPageManager() {
		super(WorkbenchPlugin.PREFERENCE_PAGE_CATEGORY_SEPARATOR,
				new WorkbenchPreferenceExpressionNode("")); //$NON-NLS-1$
	}

}
