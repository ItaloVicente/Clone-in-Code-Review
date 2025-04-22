package org.eclipse.ui.examples.rcp.mail;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;


public class MessagePopupAction extends Action {

    private final IWorkbenchWindow window;

    MessagePopupAction(String text, IWorkbenchWindow window) {
        super(text);
        this.window = window;
        setId(ICommandIds.CMD_OPEN_MESSAGE);
        setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
        setImageDescriptor(org.eclipse.ui.examples.rcp.mail.Part3Plugin.getImageDescriptor("/icons/sample3.gif"));
    }

    public void run() {
        MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
    }
}
