
package org.eclipse.ui.internal.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

class CellLayoutUtil {

    private static Point zero = new Point(0, 0);

    private static Point minimumShellSize;

    private static CellData defaultData = new CellData();

    static Point computeMinimumSize(Composite toCompute) {
        if (toCompute instanceof Shell) {
            if (minimumShellSize == null) {
                Shell testShell = new Shell((Shell) toCompute, SWT.DIALOG_TRIM
                        | SWT.RESIZE);
                testShell.setSize(0, 0);
                minimumShellSize = testShell.getSize();
                testShell.dispose();
            }

            return minimumShellSize;
        }



        return zero;
    }

    static CellData getData(Control control) {
        Object layoutData = control.getLayoutData();
        CellData data = null;

        if (layoutData instanceof CellData) {
            data = (CellData) layoutData;
        } else if (layoutData instanceof GridData) {
            data = new CellData((GridData) layoutData);
        }

        if (data == null) {
            data = defaultData;
        }

        return data;
    }
}
