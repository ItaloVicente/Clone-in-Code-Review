package org.eclipse.ui.tests.menus;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.tests.TestPlugin;

public class RemoveMarkersAction implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;


    @Override
	public void dispose() {

    }

    @Override
	public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
	public void run(IAction action) {
        try {
	        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	        
	        IMarker[] markers = root.findMarkers(AddMarkersAction.CATEGORY_TEST_MARKER, false, IResource.DEPTH_ZERO);
	        
	        for (int i = 0; i < markers.length; i++) {
	            String message = (String) markers[i].getAttribute(IMarker.MESSAGE);

	            if (message != null && message.startsWith("this is a test")) {
	                markers[i].delete();
	            }
	        }
        } catch (CoreException e) {
            openError(e);
        }
    }

    private void openError(Exception e) {
        String msg = e.getMessage();
        if (msg == null) {
            msg = e.getClass().getName();
        }

        e.printStackTrace();

        IStatus status = new Status(IStatus.ERROR, TestPlugin.getDefault()
                .getDescriptor().getUniqueIdentifier(), 0, msg, e);

        TestPlugin.getDefault().getLog().log(status);

        ErrorDialog.openError(window.getShell(), "Error", msg, status);
    }

    
    @Override
	public void selectionChanged(IAction action, ISelection selection) {

    }

}
