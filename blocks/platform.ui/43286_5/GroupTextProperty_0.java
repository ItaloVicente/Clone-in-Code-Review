package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.widgets.Group;

public class GroupTextProperty extends WidgetStringValueProperty {

	@Override
	String doGetStringValue(Object source) {
		return ((Group) source).getText();
	}

	@Override
	void doSetStringValue(Object source, String value) {
		((Group) source).setText(value == null ? "" : value); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return "Group.text <String>"; //$NON-NLS-1$
	}
}
