package org.eclipse.ui.internal;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public abstract class ActiveEditorAction extends PageEventAction {

    private IEditorPart activeEditor;

    protected ActiveEditorAction(String text, IWorkbenchWindow window) {
        super(text, window);
        updateState();
    }

    protected void editorActivated(IEditorPart part) {
    }

    protected void editorDeactivated(IEditorPart part) {
    }

    public final IEditorPart getActiveEditor() {
        return activeEditor;
    }

    @Override
	public void pageActivated(IWorkbenchPage page) {
        super.pageActivated(page);
        updateActiveEditor();
        updateState();
    }

    @Override
	public void pageClosed(IWorkbenchPage page) {
        super.pageClosed(page);
        updateActiveEditor();
        updateState();
    }

    @Override
	public void partActivated(IWorkbenchPart part) {
        super.partActivated(part);
        if (part instanceof IEditorPart) {
            updateActiveEditor();
            updateState();
        }
    }

    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
        super.partBroughtToTop(part);
        if (part instanceof IEditorPart) {
            updateActiveEditor();
            updateState();
        }
    }

    @Override
	public void partClosed(IWorkbenchPart part) {
        super.partClosed(part);
        if (part instanceof IEditorPart) {
            updateActiveEditor();
            updateState();
        }
    }

    @Override
	public void partDeactivated(IWorkbenchPart part) {
        super.partDeactivated(part);
        if (part instanceof IEditorPart) {
            updateActiveEditor();
            updateState();
        }
    }

    private void setActiveEditor(IEditorPart part) {
        if (activeEditor == part) {
            return;
        }
        if (activeEditor != null) {
            editorDeactivated(activeEditor);
        }
        activeEditor = part;
        if (activeEditor != null) {
            editorActivated(activeEditor);
        }
    }

    private void updateActiveEditor() {
        if (getActivePage() == null) {
            setActiveEditor(null);
        } else {
            setActiveEditor(getActivePage().getActiveEditor());
        }
    }

    protected void updateState() {
        setEnabled(getActiveEditor() != null);
    }

}
