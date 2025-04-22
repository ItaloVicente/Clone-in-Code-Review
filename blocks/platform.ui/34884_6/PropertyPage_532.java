package org.eclipse.ui.dialogs;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.window.SameShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.dialogs.PropertyDialog;
import org.eclipse.ui.internal.dialogs.PropertyPageContributorManager;

public class PropertyDialogAction extends SelectionProviderAction {
    private IShellProvider shellProvider;
    
	private String initialPageId;


	@Deprecated
	public PropertyDialogAction(Shell shell, ISelectionProvider provider) {
        this(new SameShellProvider(shell), provider);
	}
    
    public PropertyDialogAction(IShellProvider shell, ISelectionProvider provider) {
        super(provider, WorkbenchMessages.PropertyDialog_text); 
        Assert.isNotNull(shell);
        this.shellProvider = shell;
        setToolTipText(WorkbenchMessages.PropertyDialog_toolTip); 
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IWorkbenchHelpContextIds.PROPERTY_DIALOG_ACTION);
    }

	private boolean hasPropertyPagesFor(IStructuredSelection object) {
		return PropertyPageContributorManager.getManager().getApplicableContributors(object).size() != 0;
	}

	public boolean isApplicableForSelection() {
		if (!isEnabled()) {
			return false;
		}
		return isApplicableForSelection(getStructuredSelection());
	}

	public boolean isApplicableForSelection(IStructuredSelection selection) {
		return !selection.isEmpty() && hasPropertyPagesFor(selection);
	}

	
	@Override
	public void run() {

		PreferenceDialog dialog = createDialog();
		if (dialog != null) {
			dialog.open();
		}
	}

	public PreferenceDialog createDialog() {
		if (getStructuredSelection().isEmpty())
			return null;

		return PropertyDialog
				.createDialogOn(shellProvider.getShell(), initialPageId, getStructuredSelection());
	}

	
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(!selection.isEmpty());
	}
}
