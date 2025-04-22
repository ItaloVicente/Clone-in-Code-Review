/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.preferences;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.ui.internal.preferences.WorkingCopyPreferences;
import org.osgi.service.prefs.BackingStoreException;

/**
 * WorkingCopyManager is a concrete implementation of an
 * IWorkingCopyManager.
 * <p>
 * This class is not intended to be sub-classed by clients.
 * </p>
 * @since 3.2
 */
public class WorkingCopyManager implements IWorkingCopyManager{

	private Map workingCopies = new HashMap();


	@Override
	public IEclipsePreferences getWorkingCopy(IEclipsePreferences original) {
		if (original instanceof WorkingCopyPreferences) {
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
		for (WorkingCopyPreferences prefs : valuesArray) {
			if (prefs.nodeExists(EMPTY_STRING))
				prefs.flush();
		}
	}

}
