
package org.eclipse.ui.internal.layout;

import org.eclipse.swt.widgets.Control;

public interface ICachingLayout {
    public void flush(Control dirtyControl);
}
