
package org.eclipse.ui.internal.keys;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchWindow;

class KeyBindingState {

    private IWorkbenchWindow associatedWindow;

    private KeySequence currentSequence;

    private final IWorkbench workbench;

    KeyBindingState(IWorkbench workbenchToNotify) {
        currentSequence = KeySequence.getInstance();
        workbench = workbenchToNotify;
        associatedWindow = workbench.getActiveWorkbenchWindow();
    }

    IWorkbenchWindow getAssociatedWindow() {
        return associatedWindow;
    }

    KeySequence getCurrentSequence() {
        return currentSequence;
    }

    StatusLineContributionItem getStatusLine() {
        if (associatedWindow instanceof WorkbenchWindow) {
            WorkbenchWindow window = (WorkbenchWindow) associatedWindow;
            IStatusLineManager statusLine = window.getStatusLineManager();
            if (statusLine != null) { // this can be null if we're exiting
                IContributionItem item = statusLine
                        .find("ModeContributionItem"); //$NON-NLS-1$
                if (item instanceof StatusLineContributionItem) {
                    return ((StatusLineContributionItem) item);
                }
            }
        }

        return null;
    }

    void reset() {
        currentSequence = KeySequence.getInstance();
        updateStatusLines();
    }

    void setAssociatedWindow(IWorkbenchWindow window) {
        associatedWindow = window;
    }

    void setCurrentSequence(KeySequence sequence) {
        currentSequence = sequence;
        updateStatusLines();
    }

    private void updateStatusLines() {
        StatusLineContributionItem statusLine = getStatusLine();
        if (statusLine != null) {
            statusLine.setText(getCurrentSequence().format());
        }
    }
}
