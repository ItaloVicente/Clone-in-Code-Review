
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class ReuseEditorTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		IPreferenceStore store = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		return store.getBoolean(IPreferenceConstants.REUSE_EDITORS_BOOLEAN);
	}

}
