package org.eclipse.ui;

public interface ILocalWorkingSetManager extends IWorkingSetManager {

	public void saveState(IMemento memento);
	
	public void restoreState(IMemento memento);
}
