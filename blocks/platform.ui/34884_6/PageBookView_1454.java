package org.eclipse.ui.part;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public class PageBook extends Composite {

    public class PageBookLayout extends Layout {

        @Override
		protected Point computeSize(Composite composite, int wHint, int hHint,
                boolean flushCache) {
            if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
				return new Point(wHint, hHint);
			}

            Point result = null;
            if (currentPage != null) {
                result = currentPage.computeSize(wHint, hHint, flushCache);
            } else {
                result = new Point(0, 0);
            }
            if (wHint != SWT.DEFAULT) {
				result.x = wHint;
			}
            if (hHint != SWT.DEFAULT) {
				result.y = hHint;
			}
            return result;
        }

        @Override
		protected void layout(Composite composite, boolean flushCache) {
			if (currentPage != null && !currentPage.isDisposed()) {
                currentPage.setBounds(composite.getClientArea());
            }
        }
    }

    private Control currentPage = null;

    public PageBook(Composite parent, int style) {
        super(parent, style);
        setLayout(new PageBookLayout());
    }

    public void showPage(Control page) {
		if (page.isDisposed() || page.getParent() != this) {
			return;
		}

		currentPage = page;

		page.setVisible(true);
		layout(true);

		Control[] children = getChildren();
		for (int i = 0; i < children.length; i++) {
			Control child = children[i];
			if (child != page && !child.isDisposed()) {
				child.setVisible(false);
			}
		}
    }
}
