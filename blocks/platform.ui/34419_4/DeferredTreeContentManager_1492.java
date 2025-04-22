
package org.eclipse.ui.preferences;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.ui.internal.preferences.WorkingCopyPreferences;
import org.osgi.service.prefs.BackingStoreException;

public class WorkingCopyManager implements IWorkingCopyManager{

	private static final String EMPTY_STRING = "";//$NON-NLS-1$
	private Map workingCopies = new HashMap();

	
	@Override
	public IEclipsePreferences getWorkingCopy(IEclipsePreferences original) {
		if (original instanceof WorkingCopyPreferences) {
			throw new IllegalArgumentException("Trying to get a working copy of a working copy"); //$NON-NLS-1$
		}
		String absolutePath = original.absolutePath();
		IEclipsePreferences preferences = (IEclipsePreferences) workingCopies.get(absolutePath);
		if (preferences == null) {
			preferences = new WorkingCopyPreferences(original, this);
			workingCopies.put(absolutePath, preferences);
		}
		return preferences;
	}

	
	@Override
	public void applyChanges() throws BackingStoreException {
		Collection values = workingCopies.values();
		WorkingCopyPreferences[] valuesArray = (WorkingCopyPreferences[]) values.toArray(new WorkingCopyPreferences[values.size()]);
		for (int i = 0; i < valuesArray.length; i++) {
			WorkingCopyPreferences prefs = valuesArray[i];
			if (prefs.nodeExists(EMPTY_STRING)) 
				prefs.flush();
		}
	}

}
