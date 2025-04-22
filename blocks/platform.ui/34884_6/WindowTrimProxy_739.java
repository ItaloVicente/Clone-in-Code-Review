package org.eclipse.ui.internal;

import org.eclipse.ui.IWorkbenchWindow;

class WindowSelectionService extends AbstractSelectionService {

    private IWorkbenchWindow window;

    public WindowSelectionService(IWorkbenchWindow window) {
        setWindow(window);
    }

    private void setWindow(IWorkbenchWindow window) {
        this.window = window;
    }

    protected IWorkbenchWindow getWindow() {
        return window;
    }

    @Override
	protected AbstractPartSelectionTracker createPartTracker(String partId) {
        return new WindowPartSelectionTracker(getWindow(), partId);
    }

}
