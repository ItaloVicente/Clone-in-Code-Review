package org.eclipse.egit.ui.internal.commit;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;

public class FocusTracker {

	private final Set<Control> trackedControls = new HashSet<>();

	private final Listener listener = event -> {
		switch (event.type) {
		case SWT.Dispose:
			trackedControls.remove(event.widget);
			break;
		case SWT.FocusIn:
		case SWT.FocusOut:
			if (event.widget instanceof Control) {
				lastFocusControl = (Control) event.widget;
			}
			break;
		default:
			break;
		}
	};

	private Control lastFocusControl;

	private void hookControl(@NonNull Control control) {
		control.addListener(SWT.Dispose, listener);
		control.addListener(SWT.FocusIn, listener);
		control.addListener(SWT.FocusOut, listener);
	}

	private void unhookControl(@NonNull Control control) {
		control.removeListener(SWT.FocusOut, listener);
		control.removeListener(SWT.FocusIn, listener);
		control.removeListener(SWT.Dispose, listener);
	}

	public void addToFocusTracking(@NonNull Control control) {
		if (trackedControls.add(control)) {
			hookControl(control);
		}
	}

	public void removeFromFocusTracking(Control control) {
		if (trackedControls.remove(control) && !control.isDisposed()) {
			unhookControl(control);
		}
	}

	public Control getLastFocusControl() {
		if (lastFocusControl != null && lastFocusControl.isDisposed()) {
			trackedControls.remove(lastFocusControl);
			lastFocusControl = null;
		}
		return lastFocusControl;
	}

	public void dispose() {
		for (Control tracked : trackedControls) {
			if (!tracked.isDisposed()) {
				unhookControl(tracked);
			}
		}
		trackedControls.clear();
		lastFocusControl = null;
	}
}
