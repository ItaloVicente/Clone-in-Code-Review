package org.eclipse.ui;

public interface IWorkingSetUpdater {
	public void add(IWorkingSet workingSet);
	
	public boolean remove(IWorkingSet workingSet);
	
	public boolean contains(IWorkingSet workingSet);
	
	public void dispose();
}
