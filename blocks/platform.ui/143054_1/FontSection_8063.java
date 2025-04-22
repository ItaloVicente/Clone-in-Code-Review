package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class FontPropertyDescriptor
	extends PropertyDescriptor {

	public FontPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FontDialogCellEditor(parent);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

}
