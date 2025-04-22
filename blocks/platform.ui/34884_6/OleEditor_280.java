package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

public class TextPropertyDescriptor extends PropertyDescriptor {
    public TextPropertyDescriptor(Object id, String displayName) {
        super(id, displayName);
    }

    @Override
	public CellEditor createPropertyEditor(Composite parent) {
        CellEditor editor = new TextCellEditor(parent);
        if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
        return editor;
    }
}
