package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;

@FunctionalInterface
public interface MenuFunction {

	public Menu createMenu(Decorations parent);
}
