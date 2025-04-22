package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;

public interface IWorkingSetUpdater2 extends IWorkingSetUpdater {

	boolean isManagingPersistenceOf(IWorkingSet set);

	IAdaptable[] restore(IWorkingSet set);
}
