
package org.eclipse.ui.menus;

import org.eclipse.jface.menus.AbstractTrimWidget;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchWindow;

public abstract class AbstractWorkbenchTrimWidget extends AbstractTrimWidget implements IWorkbenchWidget {
	
	private IWorkbenchWindow wbWindow;

	public AbstractWorkbenchTrimWidget() {
		super();
	}
	

	@Override
	public void init(IWorkbenchWindow workbenchWindow) {
		wbWindow = workbenchWindow;
	}
	
	public IWorkbenchWindow getWorkbenchWindow() {
		return wbWindow;
	}
	
	public Point getPreferredSize() {
		return null;
	}
}
