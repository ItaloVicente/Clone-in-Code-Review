package org.eclipse.ui.tests.dialogs;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class FontFieldEditorTestPreferencePage extends
        FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    public FontFieldEditorTestPreferencePage() {
        super(GRID);
    }

    @Override
	protected void createFieldEditors() {

        Composite feParent = getFieldEditorParent();

        for (int i = 0; i < 3; i++) {
            addField(new FontFieldEditor("FontValue" + String.valueOf(i),
                    "Font Test" + String.valueOf(i), "Preview", feParent));

            addField(new FontFieldEditor(
                    "FontValueDefault" + String.valueOf(i), "Font Test Default"
                            + String.valueOf(i), feParent));
        }

    }

    @Override
	public void init(IWorkbench workbench) {
    }

}
