package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Widget;

@FunctionalInterface
public interface WidgetSupplier<W extends Widget, P extends Widget> {

	W create(P parent);
}
