package org.eclipse.ui.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ImportResourcesAction extends BaseSelectionListenerAction
        implements ActionFactory.IWorkbenchAction {
    private ActionFactory.IWorkbenchAction action;
    
    private IWorkbenchWindow workbenchWindow;

    public ImportResourcesAction(IWorkbenchWindow window) {
        super(WorkbenchMessages.ImportResourcesAction_text);
        if (window == null) {
            throw new IllegalArgumentException();
        }
        
        this.workbenchWindow = window;
        action = ActionFactory.IMPORT.create(window);
        
        setText(action.getText()); 
        setToolTipText(action.getToolTipText());
        setId(action.getId());
        setActionDefinitionId(action.getActionDefinitionId());
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.IMPORT_ACTION);
        setImageDescriptor(action.getImageDescriptor());
    }

    @Deprecated
	public ImportResourcesAction(IWorkbench workbench) {
        this(workbench.getActiveWorkbenchWindow());
    }

    @Override
	public void run() {
        if (workbenchWindow == null) {
            return;
        }
        
        action.run();
    }

    @Deprecated
	public void setSelection(IStructuredSelection selection) {
        selectionChanged(selection);
    }

    @Override
	public void dispose() {
    	workbenchWindow = null;
    	if (action!=null) {
    	action.dispose();
    	}
    	action = null;
    }
}
