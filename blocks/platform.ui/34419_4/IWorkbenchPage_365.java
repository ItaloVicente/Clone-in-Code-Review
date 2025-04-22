package org.eclipse.ui;

public interface IWorkbenchListener {
	
    public boolean preShutdown(IWorkbench workbench, boolean forced);

    public void postShutdown(IWorkbench workbench);

}
