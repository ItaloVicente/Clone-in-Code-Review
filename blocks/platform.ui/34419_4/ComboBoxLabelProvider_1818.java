package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.swt.widgets.Composite;

public class ColorPropertyDescriptor extends PropertyDescriptor {
    public ColorPropertyDescriptor(Object id, String displayName) {
        super(id, displayName);
    }

    @Override
	public CellEditor createPropertyEditor(Composite parent) {
        CellEditor editor = new ColorCellEditor(parent);
        if (getValidator() != null) {
			editor.setValidator(getValidator());
		}
        return editor;
    }
}
