package org.eclipse.ui.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public interface IWorkingCopyManager {
	public IEclipsePreferences getWorkingCopy(IEclipsePreferences original);

	public void applyChanges() throws BackingStoreException;


}
