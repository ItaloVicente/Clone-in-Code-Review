package org.eclipse.ui.forms.widgets;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public class LayoutComposite extends Composite {

	public LayoutComposite(Composite parent, int style) {
		super(parent, style);
		setMenu(parent.getMenu());
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Layout layout = getLayout();

		if (layout instanceof TableWrapLayout) {
			return ((TableWrapLayout) layout).computeSize(this, wHint, hHint, changed);
		}
		if (layout instanceof ColumnLayout) {
			return ((ColumnLayout) layout).computeSize(this, wHint, hHint, changed);
		}
		return super.computeSize(wHint, hHint, changed);
	}
}
