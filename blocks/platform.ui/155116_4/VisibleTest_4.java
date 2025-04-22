
package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Widget;

public class WidgetVisibleProperty<S extends Widget> extends WidgetDelegatingValueProperty<S, Boolean> {
	private IValueProperty<S, Boolean> control;
	private IValueProperty<S, Boolean> menu;
	private IValueProperty<S, Boolean> toolBar;
	private IValueProperty<S, Boolean> scrollBar;
	private IValueProperty<S, Boolean> toolTip;

	public WidgetVisibleProperty() {
		super(String.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IValueProperty<S, Boolean> doGetDelegate(S source) {
		if (source instanceof Control) {
			if (control == null) {
				control = (IValueProperty<S, Boolean>) new ControlVisibleProperty<Control>();
			}
			return control;
		}
		if (source instanceof Menu) {
			if (menu == null) {
				menu = (IValueProperty<S, Boolean>) new MenuVisibleProperty();
			}
			return menu;
		}
		if (source instanceof ToolBar) {
			if (toolBar == null) {
				toolBar = (IValueProperty<S, Boolean>) new ToolBarVisibleProperty();
			}
			return toolBar;
		}
		if (source instanceof ScrollBar) {
			if (scrollBar == null) {
				scrollBar = (IValueProperty<S, Boolean>) new ScrollBarVisibleProperty();
			}
			return scrollBar;
		}
		if (source instanceof ToolTip) {
			if (toolTip == null) {
				toolTip = (IValueProperty<S, Boolean>) new ToolTipVisibleProperty();
			}
			return toolTip;
		}
		throw notSupported(source);
	}
}
