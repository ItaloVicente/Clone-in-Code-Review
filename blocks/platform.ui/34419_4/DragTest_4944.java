package org.eclipse.ui.tests.dnd;

import junit.framework.Assert;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.internal.PartPane;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.dnd.DragUtil;
import org.eclipse.ui.internal.dnd.TestDropLocation;

public class DragOperations {

    public static void drag(IWorkbenchPart part, TestDropLocation target,
            boolean wholeFolder) {
        DragUtil.forceDropLocation(target);


        Assert.fail("DND needs some updating");
        DragUtil.forceDropLocation(null);
    }

    public static String getName(IEditorPart editor) {
        IWorkbenchPage page = editor.getSite().getPage();
        IWorkbenchPartReference ref = page.getReference(editor);
        return ref.getPartName();
    }

    public static PartPane getPane(IEditorPart editor) {
        return null;
    }

    public static PartPane getPane(IViewPart view) {
        return null;
    }

    public static Rectangle getDisplayBounds(PartPane pane) {

        return new Rectangle(0, 0, 0, 0);
    }

    public static Point getLocation(PartPane pane, int side) {

        return DragOperations.getPoint(getDisplayBounds(pane), side);
    }

    public static Point getPointInEditorArea(WorkbenchPage page, int side) {
    	return new Point(0, 0);
    }

    public static Point getPoint(Rectangle bounds, int side) {
        Point centerPoint = Geometry.centerPoint(bounds);

        switch (side) {
        case SWT.TOP:
            return new Point(centerPoint.x, bounds.y + 1);
        case SWT.BOTTOM:
            return new Point(centerPoint.x, bounds.y + bounds.height - 1);
        case SWT.LEFT:
            return new Point(bounds.x + 1, centerPoint.y);
        case SWT.RIGHT:
            return new Point(bounds.x + bounds.width - 1, centerPoint.y);
        }

        return centerPoint;
    }

    public static String nameForConstant(int swtSideConstant) {
        switch (swtSideConstant) {
        case SWT.TOP:
            return "top";
        case SWT.BOTTOM:
            return "bottom";
        case SWT.LEFT:
            return "left";
        case SWT.RIGHT:
            return "right";
        }

        return "center";
    }

    public static String getName(IViewPart targetPart) {
        return targetPart.getTitle();
    }

    public static String getLayoutDescription(WorkbenchPage page) {
        StringBuffer buf = new StringBuffer();

        buf.append("this layout still not quite described - TODO");
        return buf.toString();
    }
}
