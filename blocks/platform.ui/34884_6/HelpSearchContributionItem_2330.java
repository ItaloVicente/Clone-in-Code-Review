package org.eclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.util.PrefUtil;

public class HelpSearchAction extends Action implements IWorkbenchAction {
    private IWorkbenchWindow workbenchWindow;

    public HelpSearchAction() {
        this(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }

    public HelpSearchAction(IWorkbenchWindow window) {
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.workbenchWindow = window;
        setActionDefinitionId(IWorkbenchCommandConstants.HELP_HELP_SEARCH);

        String overrideText = PrefUtil.getAPIPreferenceStore().getString(
                IWorkbenchPreferenceConstants.HELP_SEARCH_ACTION_TEXT);
        if ("".equals(overrideText)) { //$NON-NLS-1$
            setText(WorkbenchMessages.HelpSearchAction_text); 
            setToolTipText(WorkbenchMessages.HelpSearchAction_toolTip);
        } else {
            setText(overrideText);
            setToolTipText(Action.removeMnemonics(overrideText));
        }
        setImageDescriptor(WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HELP_SEARCH));
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.HELP_SEARCH_ACTION);
    }

    @Override
	public void run() {
        if (workbenchWindow == null) {
            return;
        }
        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
            	workbenchWindow.getWorkbench().getHelpSystem().displaySearch();
            }
        });
    }

    @Override
	public void dispose() {
        workbenchWindow = null;
    }

}
