
package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.widgets.ToolBar;

public class ToolBarVisibleProperty extends WidgetBooleanValueProperty<ToolBar> {
	@Override
	boolean doGetBooleanValue(ToolBar source) {
		return source.getVisible();
	}

	@Override
	void doSetBooleanValue(ToolBar source, boolean value) {
		source.setVisible(value);
	}

	@Override
	public String toString() {
		return "ToolBar.visible <boolean>"; //$NON-NLS-1$
	}
}
