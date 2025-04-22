package org.eclipse.ui.internal.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

public interface TestDropLocation {

    public Point getLocation();
    
    public Shell[] getShells();
}
