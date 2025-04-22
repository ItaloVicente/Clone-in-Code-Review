package org.eclipse.ui.internal.dnd;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

public class CompatibilityDragTarget {

    private final static int MARGIN = 30;

    public static int getRelativePosition(Control c, Point toTest) {
        Point p = c.toControl(toTest);
        Point e = c.getSize();

        if (p.x > e.x || p.y > e.y || p.x < 0 || p.y < 0) {
            return SWT.DEFAULT;
        }

        int hmargin = Math.min(e.x / 3, MARGIN);
        int vmargin = Math.min(e.y / 3, MARGIN);

        Rectangle inner = new Rectangle(hmargin, vmargin, e.x - (hmargin * 2),
                e.y - (vmargin * 2));
        if (inner.contains(p)) {
            return SWT.CENTER;
        } else {
            return Geometry.getClosestSide(inner, p);
        }
    }

}
