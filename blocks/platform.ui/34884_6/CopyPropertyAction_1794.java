package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ComboBoxPropertyDescriptor extends PropertyDescriptor {

    private String[] labels;

    public ComboBoxPropertyDescriptor(Object id, String displayName,
            String[] labelsArray) {
        super(id, displayName);
        labels = labelsArray;
    }

    @Override
	public CellEditor createPropertyEditor(Composite parent) {
        CellEditor editor = new ComboBoxCellEditor(parent, labels,
                SWT.READ_ONLY);
        if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
        return editor;
    }

    @Override
	public ILabelProvider getLabelProvider() {
        if (isLabelProviderSet()) {
			return super.getLabelProvider();
		}
		return new ComboBoxLabelProvider(labels);
    }
}
