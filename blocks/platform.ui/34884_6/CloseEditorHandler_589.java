package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

public class CloseAllSavedAction extends PageEventAction implements
        IPropertyListener {

    private List partsWithListeners = new ArrayList(1);

    public CloseAllSavedAction(IWorkbenchWindow window) {
        super(WorkbenchMessages.CloseAllSavedAction_text, window);
        setToolTipText(WorkbenchMessages.CloseAllSavedAction_toolTip);
        setId("closeAllSaved"); //$NON-NLS-1$
        updateState();
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.CLOSE_ALL_SAVED_ACTION);
        setActionDefinitionId("org.eclipse.ui.file.closeAllSaved"); //$NON-NLS-1$
    }

    @Override
	public void pageActivated(IWorkbenchPage page) {
        super.pageActivated(page);
        updateState();
    }

    @Override
	public void pageClosed(IWorkbenchPage page) {
        super.pageClosed(page);
        updateState();
    }

    @Override
	public void partClosed(IWorkbenchPart part) {
        super.partClosed(part);
        if (part instanceof IEditorPart) {
            part.removePropertyListener(this);
            partsWithListeners.remove(part);
            updateState();
        }
    }

    @Override
	public void partOpened(IWorkbenchPart part) {
        super.partOpened(part);
        if (part instanceof IEditorPart) {
            part.addPropertyListener(this);
            partsWithListeners.add(part);
            updateState();
        }
    }

    @Override
	public void propertyChanged(Object source, int propID) {
        if (source instanceof IEditorPart) {
            if (propID == IEditorPart.PROP_DIRTY) {
                updateState();
            }
        }
    }

    @Override
	public void run() {
        if (getWorkbenchWindow() == null) {
            return;
        }
        IWorkbenchPage page = getActivePage();
        if (page != null) {
            ((WorkbenchPage) page).closeAllSavedEditors();
        }
    }

    private void updateState() {
        IWorkbenchPage page = getActivePage();
        if (page == null) {
            setEnabled(false);
            return;
        }
        IEditorReference editors[] = page.getEditorReferences();
        for (int i = 0; i < editors.length; i++) {
            if (!editors[i].isDirty()) {
                setEnabled(true);
                return;
            }
        }
        setEnabled(false);
    }

    @Override
	public void dispose() {
        super.dispose();
        for (Iterator it = partsWithListeners.iterator(); it.hasNext();) {
            IWorkbenchPart part = (IWorkbenchPart) it.next();
            part.removePropertyListener(this);
        }
        partsWithListeners.clear();
    }

}
