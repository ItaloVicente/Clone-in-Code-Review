package org.eclipse.e4.ui.workbench.swt;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

public final class DisplayUISynchronize extends UISynchronize {

	private Display display;

	public DisplayUISynchronize(Display display) {
		this.display = display;
	}

	@Override
	public void syncExec(Runnable runnable) {
		if (display != null && !display.isDisposed()) {
			display.syncExec(runnable);
		}
	}

	@Override
	public void asyncExec(Runnable runnable) {
		if (display != null && !display.isDisposed()) {
			display.asyncExec(runnable);
		}
	}

	@Override
	public boolean isUIThread(Thread thread) {
		return Display.findDisplay(thread) != null;
	}

	@Override
	protected void showBusyWhile(Runnable runnable) {
		BusyIndicator.showWhile(display, runnable);
	}

	@Override
	protected boolean dispatchEvents() {
		if (display != null && !display.isDisposed()) {
			return display.readAndDispatch();
		}
		return false;
	}
}
