package org.eclipse.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;

@Deprecated
public class YesNoCancelListSelectionDialog extends ListSelectionDialog {
    @Deprecated
	public YesNoCancelListSelectionDialog(
            org.eclipse.swt.widgets.Shell parentShell,
            Object input,
            org.eclipse.jface.viewers.IStructuredContentProvider contentProvider,
            org.eclipse.jface.viewers.ILabelProvider labelProvider,
            String message) {
        super(parentShell, input, contentProvider, labelProvider, message);
    }

    @Override
	protected void buttonPressed(int buttonId) {
        switch (buttonId) {
        case IDialogConstants.YES_ID: {
            yesPressed();
            return;
        }
        case IDialogConstants.NO_ID: {
            noPressed();
            return;
        }
        case IDialogConstants.CANCEL_ID: {
            cancelPressed();
            return;
        }
        }
    }

    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(shell,
                IWorkbenchHelpContextIds.YES_NO_CANCEL_LIST_SELECTION_DIALOG);
    }

    @Override
	protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.YES_ID,
                IDialogConstants.YES_LABEL, true);
        createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL,
                false);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
    }

    protected void noPressed() {
        setReturnCode(IDialogConstants.NO_ID);
        close();
    }

    protected void yesPressed() {
        okPressed();
        setReturnCode(IDialogConstants.YES_ID);
    }
}
