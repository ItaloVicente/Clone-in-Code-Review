
package org.eclipse.ui.internal.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class CacheWrapper {
    private Composite proxy;

    private SizeCache cache = new SizeCache();

    private Rectangle lastBounds = new Rectangle(0, 0, 0, 0);

    private class WrapperLayout extends Layout implements ICachingLayout {
        @Override
		protected Point computeSize(Composite composite, int wHint, int hHint,
                boolean flushCache) {
            Control[] children = composite.getChildren();
            if (children.length != 1) {
                return new Point(0, 0);
            }

            cache.setControl(children[0]);

            return cache.computeSize(wHint, hHint);
        }

        @Override
		protected void layout(Composite composite, boolean flushCache) {
            Control[] children = composite.getChildren();
            if (children.length != 1) {
                return;
            }

            Control child = children[0];
            Rectangle newBounds = composite.getClientArea();
            if (!newBounds.equals(lastBounds)) {
                child.setBounds(newBounds);
                lastBounds = newBounds;
            }

        }

        @Override
		public void flush(Control dirtyControl) {
            CacheWrapper.this.flushCache();
        }
    }

    public CacheWrapper(Composite parent) {
        proxy = new Composite(parent, SWT.NONE);

        proxy.setLayout(new WrapperLayout());
    }

    public void flushCache() {
        cache.flush();
    }

    public Composite getControl() {
        return proxy;
    }

    public void dispose() {
        if (proxy != null) {
            proxy.dispose();
            proxy = null;
        }
    }
}
