package org.eclipse.e4.ui.workbench.addons.minmax;

import org.eclipse.e4.ui.di.UISynchronize;

public class BogusUISynchronize extends UISynchronize {
	@Override
	public void syncExec(Runnable runnable) {
		runnable.run();
	}

	@Override
	public void asyncExec(Runnable runnable) {
		runnable.run();
	}

	@Override
	public boolean isUIThread(Thread thread) {
		return false;
	}

	@Override
	protected void showBusyWhile(Runnable runnable) {
		runnable.run();
	}

	@Override
	protected boolean dispatchEvents() {
		return false;
	}
}
