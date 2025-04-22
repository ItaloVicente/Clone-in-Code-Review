
package org.eclipse.e4.ui.tests;

import org.eclipse.e4.ui.di.UISynchronize;

public class UISynchronizeTestImpl extends UISynchronize {
	@Override
	public void syncExec(Runnable runnable) {
		runnable.run();
	}

	@Override
	public void asyncExec(final Runnable runnable) {
		runnable.run();
	}
}
