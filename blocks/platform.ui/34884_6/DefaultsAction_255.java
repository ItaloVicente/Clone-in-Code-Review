
package org.eclipse.ui.views.properties;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.views.properties.PropertiesMessages;

    private Clipboard clipboard;

    public CopyPropertyAction(PropertySheetViewer viewer, String name,
            Clipboard clipboard) {
        super(viewer, name);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IPropertiesHelpContextIds.COPY_PROPERTY_ACTION);
        this.clipboard = clipboard;
    }

    @Override
	public void run() {
        IStructuredSelection selection = (IStructuredSelection) getPropertySheet()
                .getSelection();
        if (selection.isEmpty()) {
			return;
		}
        IPropertySheetEntry entry = (IPropertySheetEntry) selection
                .getFirstElement();

        StringBuffer buffer = new StringBuffer();
        buffer.append(entry.getDisplayName());
        buffer.append("\t"); //$NON-NLS-1$
        buffer.append(entry.getValueAsString());

        setClipboard(buffer.toString());
    }

    public void selectionChanged(IStructuredSelection sel) {
        setEnabled(!sel.isEmpty());
    }

    private void setClipboard(String text) {
        try {
            Object[] data = new Object[] { text };
            Transfer[] transferTypes = new Transfer[] { TextTransfer
                    .getInstance() };
            clipboard.setContents(data, transferTypes);
        } catch (SWTError e) {
            if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
				throw e;
			}
            if (MessageDialog.openQuestion(getPropertySheet().getControl()
                    .getShell(), PropertiesMessages.CopyToClipboardProblemDialog_title,
                    PropertiesMessages.CopyToClipboardProblemDialog_message)) {
				setClipboard(text);
			}
        }
    }
}

