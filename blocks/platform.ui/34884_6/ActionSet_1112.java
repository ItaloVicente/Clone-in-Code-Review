
package org.eclipse.ui.internal.menus;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.IWorkbenchWidget;

public class AbstractWorkbenchWidget implements IWorkbenchWidget {

	@Override
	public void init(IWorkbenchWindow workbenchWindow) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void fill(Composite parent) {

	}

	@Override
	public void fill(Menu parent, int index) {

	}

	@Override
	public void fill(ToolBar parent, int index) {

	}

	@Override
	public void fill(CoolBar parent, int index) {

	}

	public Point getPreferredSize() {
		return null;
	}

}
