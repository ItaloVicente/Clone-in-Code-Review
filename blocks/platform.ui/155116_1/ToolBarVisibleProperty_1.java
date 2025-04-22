
package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.widgets.ScrollBar;

public class ScrollBarVisibleProperty extends WidgetBooleanValueProperty<ScrollBar> {
	@Override
	boolean doGetBooleanValue(ScrollBar source) {
		return source.getVisible();
	}

	@Override
	void doSetBooleanValue(ScrollBar source, boolean value) {
		source.setVisible(value);
	}

	@Override
	public String toString() {
		return "ScrollBar.visible <boolean>"; //$NON-NLS-1$
	}
}
