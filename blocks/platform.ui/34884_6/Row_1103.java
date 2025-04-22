
package org.eclipse.ui.internal.layout;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

public class LayoutUtil {

    public static void resize(Control changedControl) {
        Composite parent = changedControl.getParent();

        Layout parentLayout = parent.getLayout();

        if (parentLayout instanceof ICachingLayout) {
            ((ICachingLayout) parentLayout).flush(changedControl);
        }

        if (parent instanceof Shell) {
            parent.layout(true);
        } else {
            Rectangle currentBounds = parent.getBounds();

            resize(parent);

            if (currentBounds.equals(parent.getBounds())) {
                parent.layout(true);
            }
        }
    }
}
