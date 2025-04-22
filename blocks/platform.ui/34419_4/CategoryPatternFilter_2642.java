
package org.eclipse.ui.internal.keys;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

final class CancelOnModifyListener implements Listener {

    private final Listener chainedListener;

    CancelOnModifyListener(Listener listener) {
        chainedListener = listener;
    }

    @Override
	public void handleEvent(Event event) {
        Widget widget = event.widget;
        widget.removeListener(SWT.Modify, this);
        widget.removeListener(SWT.KeyDown, chainedListener);
    }
}
