package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Widget;

@FunctionalInterface
public interface Property<T extends Widget> {

	void apply(T widget);
}
