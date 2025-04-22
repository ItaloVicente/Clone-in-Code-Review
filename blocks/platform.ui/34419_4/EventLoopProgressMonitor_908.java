package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ErrorPreferencePage extends EmptyPreferencePage {

    @Override
	protected Control createContents(Composite parent) {
        Text text = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP);
        text.setForeground(JFaceColors.getErrorText(text.getDisplay()));
        text.setBackground(text.getDisplay().getSystemColor(
                SWT.COLOR_WIDGET_BACKGROUND));
        text.setText(WorkbenchMessages.ErrorPreferencePage_errorMessage); 
        return text;
    }
}
