
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

public class ActionAddGlobalTask extends Action {

    private static final String ENABLED_IMAGE_PATH = "elcl16/addtsk_tsk.png"; //$NON-NLS-1$

    private IWorkbenchPart part;

    public ActionAddGlobalTask(IWorkbenchPart part) {
        setText(MarkerMessages.addGlobalTaskAction_title);
        setImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor(ENABLED_IMAGE_PATH));
        setToolTipText(MarkerMessages.addGlobalTaskAction_tooltip);
        this.part = part;
    }

    @Override
	public void run() {
        DialogTaskProperties dialog = new DialogTaskProperties(part.getSite()
                .getShell(), MarkerMessages.addGlobalTaskDialog_title);
        dialog.open();
    }
}
