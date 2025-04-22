package org.eclipse.ui.examples.readmetool;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class SectionsDialog extends Dialog {
    protected IAdaptable input;

    public SectionsDialog(Shell parentShell, IAdaptable input) {
        super(parentShell);
        this.input = input;
    }

    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(MessageUtil.getString("Readme_Sections")); //$NON-NLS-1$
        PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell,
				IReadmeConstants.SECTIONS_DIALOG_CONTEXT);
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        List list = new List(composite, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        list.setLayoutData(data);
        ListViewer viewer = new ListViewer(list);
        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setInput(input);

        return composite;
    }
}
