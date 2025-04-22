package org.eclipse.ui.tests.performance.layout;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.WorkbenchException;

public abstract class TestWidgetFactory {
	public Point getMaxSize() throws CoreException, WorkbenchException {
	    Composite control = getControl();
	    Composite parent = control.getParent();
	    
	    if (parent == null) {
	        return new Point(800, 600);
	    }
	    return Geometry.getSize(parent.getClientArea());
	}
	public void init() throws CoreException, WorkbenchException {}
	public void done() throws CoreException, WorkbenchException {}
	public abstract String getName();
	public abstract Composite getControl() throws CoreException, WorkbenchException;
}
