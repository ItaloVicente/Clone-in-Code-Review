
package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.widgets.Menu;

public class MenuVisibleProperty extends WidgetBooleanValueProperty<Menu> {
	@Override
	boolean doGetBooleanValue(Menu source) {
		return source.getVisible();
	}

	@Override
	void doSetBooleanValue(Menu source, boolean value) {
		source.setVisible(value);
	}

	@Override
	public String toString() {
		return "Menu.visible <boolean>"; //$NON-NLS-1$
	}
}
