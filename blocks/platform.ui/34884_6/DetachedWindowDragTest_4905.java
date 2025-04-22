package org.eclipse.ui.tests.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.dnd.TestDropLocation;

public class DetachedDropTarget implements TestDropLocation {

    @Override
	public String toString() {
        return "out of the window";
    }

    @Override
	public Point getLocation() {
        return new Point(0,0);
    }

    @Override
	public Shell[] getShells() {
        return new Shell[0];
    }
}
