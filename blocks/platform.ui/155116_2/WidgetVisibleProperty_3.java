
package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.widgets.ToolTip;

public class ToolTipVisibleProperty extends WidgetBooleanValueProperty<ToolTip> {
	@Override
	boolean doGetBooleanValue(ToolTip source) {
		return source.getVisible();
	}

	@Override
	void doSetBooleanValue(ToolTip source, boolean value) {
		source.setVisible(value);
	}

	@Override
	public String toString() {
		return "ToolTip.visible <boolean>"; //$NON-NLS-1$
	}
}
