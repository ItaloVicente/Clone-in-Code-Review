package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.PlatformUI;

public class FontDialogCellEditor
    extends DialogCellEditor {

    protected FontDialogCellEditor(Composite parent) {
        super(parent);
    }

    protected Object openDialogBox(Control cellEditorWindow) {
        FontDialog ftDialog = new FontDialog(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell());

        String value = (String) getValue();

        if ((value != null) && (value.length() > 0)) {
            ftDialog.setFontList(new FontData[] {new FontData(value)});
        }
        FontData fData = ftDialog.open();

        if (fData != null) {
            value = fData.toString();
        }
        return value;
    }

}
