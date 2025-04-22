package org.eclipse.ui.application;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.WindowManager;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;

public interface IWorkbenchConfigurer {

    public static final int RESTORE_CODE_RESET = 1;

    public static final int RESTORE_CODE_EXIT = 2;

    public IWorkbench getWorkbench();

    public boolean getSaveAndRestore();

    public void setSaveAndRestore(boolean enabled);
	
	public IWorkbenchWindowConfigurer restoreWorkbenchWindow(IMemento memento)
			throws WorkbenchException;

    public WindowManager getWorkbenchWindowManager();

    public void declareImage(String symbolicName, ImageDescriptor descriptor,
            boolean shared);

    public void emergencyClose();

    public boolean emergencyClosing();

    public IWorkbenchWindowConfigurer getWindowConfigurer(
            IWorkbenchWindow window);

    public Object getData(String key);

    public void setData(String key, Object data);

    public IStatus restoreState();

    public void openFirstTimeWindow();
    
    public boolean getExitOnLastWindowClose();
    
    public void setExitOnLastWindowClose(boolean enabled);
}
