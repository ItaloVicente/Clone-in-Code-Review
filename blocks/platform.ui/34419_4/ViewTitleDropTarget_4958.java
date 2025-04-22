package org.eclipse.ui.tests.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.views.IViewDescriptor;

public class ViewTabDropTarget extends WorkbenchWindowDropTarget {

    String targetPart;

    public ViewTabDropTarget(IWorkbenchWindowProvider provider, String part) {
        super(provider);
        targetPart = part;
    }

    IViewPart getPart() {
        return getPage().findView(targetPart);
    }

    @Override
	public String toString() {
        IViewDescriptor desc = WorkbenchPlugin.getDefault().getViewRegistry()
                .find(targetPart);
        String title = desc.getLabel();

        return title + " view tab area";
    }

    @Override
	public Point getLocation() {
        Rectangle bounds = DragOperations.getDisplayBounds(DragOperations
                .getPane(getPart()));

        return new Point(bounds.x + 8, bounds.y + 8);
    }

    @Override
	public Shell getShell() {
    	return getPart().getSite().getShell();
    }
}
