package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class CheckBoxPropertyDescriptor
	extends PropertyDescriptor {

	public CheckBoxPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new CheckboxCellEditor(parent);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
}
