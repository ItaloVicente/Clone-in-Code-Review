package org.eclipse.ui.internal.preferences.esnapshot;

import java.util.Map;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IPreferenceFilter;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.WorkbenchPlugin;

abstract class AbstractEclipseSnapshotPrefImpExp {
	protected IPreferenceFilter[] getSetAllFilter() {
		IPreferenceFilter[] transfers = new IPreferenceFilter[1];
		transfers[0] = new IPreferenceFilter() {

			@Override
			public String[] getScopes() {
				return new String[] { InstanceScope.SCOPE, ConfigurationScope.SCOPE };
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Map getMapping(String scope) {
				return null;
			}
		};
		return transfers;
	}

	protected void reportError(Shell shell, Exception e) {
		WorkbenchPlugin.log(e.getMessage(), e);
		MessageDialog.open(MessageDialog.ERROR, shell, "", e.getLocalizedMessage(), SWT.SHEET); //$NON-NLS-1$
	}
}
