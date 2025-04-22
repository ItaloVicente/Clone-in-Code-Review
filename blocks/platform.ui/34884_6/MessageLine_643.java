package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.ui.ILocalWorkingSetManager;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkingSet;
import org.osgi.framework.BundleContext;


public class LocalWorkingSetManager extends AbstractWorkingSetManager implements ILocalWorkingSetManager {

	public LocalWorkingSetManager(BundleContext context) {
		super(context);
	}

	@Override
	public void removeWorkingSet(IWorkingSet workingSet) {
		internalRemoveWorkingSet(workingSet);
	}

	@Override
	public void addRecentWorkingSet(IWorkingSet workingSet) {
		internalAddRecentWorkingSet(workingSet);
	}

	@Override
	public void saveState(IMemento memento) {
        saveWorkingSetState(memento);
        saveMruList(memento);
	}
	
	@Override
	public void restoreState(IMemento memento) {
		Assert.isNotNull(memento);
		Assert.isTrue(getWorkingSets().length == 0);
        restoreWorkingSetState(memento);
        restoreMruList(memento);
	}
}
