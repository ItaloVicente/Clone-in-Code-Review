package org.eclipse.ui.internal;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;

public class ToggleEditorsVisibilityAction extends PerspectiveAction implements
        IPerspectiveListener {

    @Override
	public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
        if (page.isEditorAreaVisible()) {
            setText(WorkbenchMessages.ToggleEditor_hideEditors); 
        } else {
            setText(WorkbenchMessages.ToggleEditor_showEditors);
        }
    }

    @Override
	public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId) {
        if (changeId == IWorkbenchPage.CHANGE_RESET
                || changeId == IWorkbenchPage.CHANGE_EDITOR_AREA_HIDE
                || changeId == IWorkbenchPage.CHANGE_EDITOR_AREA_SHOW) {
            if (page.isEditorAreaVisible()) {
                setText(WorkbenchMessages.ToggleEditor_hideEditors); 
            } else {
                setText(WorkbenchMessages.ToggleEditor_showEditors); 
            }
        }
    }

    public ToggleEditorsVisibilityAction(IWorkbenchWindow window) {
        super(window);
        setText(WorkbenchMessages.ToggleEditor_hideEditors);
        setActionDefinitionId("org.eclipse.ui.window.hideShowEditors"); //$NON-NLS-1$
        setToolTipText(WorkbenchMessages.ToggleEditor_toolTip);
        window.getWorkbench().getHelpSystem().setHelp(this,
                IWorkbenchHelpContextIds.TOGGLE_EDITORS_VISIBILITY_ACTION);
        window.addPerspectiveListener(this);
    }

    @Override
	protected void run(IWorkbenchPage page, IPerspectiveDescriptor persp) {
        boolean visible = page.isEditorAreaVisible();
        if (visible) {
            page.setEditorAreaVisible(false);
            setText(WorkbenchMessages.ToggleEditor_showEditors); 
        } else {
            page.setEditorAreaVisible(true);
            setText(WorkbenchMessages.ToggleEditor_hideEditors);
        }
    }

    @Override
	public void dispose() {
        if (getWindow() != null) {
            getWindow().removePerspectiveListener(this);
        }
        super.dispose();
    }

}
