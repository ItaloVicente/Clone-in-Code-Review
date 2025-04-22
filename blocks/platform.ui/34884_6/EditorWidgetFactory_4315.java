package org.eclipse.ui.tests.performance.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public class ConstantAreaLayout extends Layout {

    private int area;
    private int preferredWidth;
    
    public ConstantAreaLayout(int area, int preferredWidth) {
        this.area = area;
        this.preferredWidth = preferredWidth;
    }
    
    protected Point computeSize(Composite composite, int wHint, int hHint,
            boolean flushCache) {
        
        if (wHint == 0 || hHint == 0) {
            return new Point(1,1);
        }
        
        if (wHint == SWT.DEFAULT) {
            if (hHint == SWT.DEFAULT) {
                wHint = preferredWidth;
            } else {
                wHint = area / hHint;
            }
        }
        
        if (hHint == SWT.DEFAULT) {
            hHint = area / wHint;
        }
        
        return new Point(wHint, hHint);
    }

    protected void layout(Composite composite, boolean flushCache) {

    }

}
