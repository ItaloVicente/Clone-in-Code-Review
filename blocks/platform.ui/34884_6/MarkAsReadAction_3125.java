package org.eclipse.e4.ui.examples.css.rcp;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;


public class MarkAsReadAction extends Action {

    private final IWorkbenchWindow window;

    MarkAsReadAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        setId(ICommandIds.CMD_MARK_AS_READ);
        setActionDefinitionId(ICommandIds.CMD_MARK_AS_READ);
        setImageDescriptor(org.eclipse.e4.ui.examples.css.rcp.Activator.getImageDescriptor("/icons/sample3.gif"));
    }

    public void run() {
    	
		IWorkbenchPart part = window.getActivePage().getActivePart();		
		IViewReference[] viewRefs = window.getActivePage().getViewReferences();

		for (int i = 0; i < viewRefs.length; i++) {		
			IViewReference viewReference = viewRefs[i];
			if(viewReference.getId().equals(View.ID)) {
				View messageView = (View) viewReference.getPart(false);
				if(messageView.isTopMost()) {
					messageView.markAsRead();
					return;
				}
			}
		}
    }
}
