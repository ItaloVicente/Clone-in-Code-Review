
package org.eclipse.ui.application;

import org.eclipse.ui.internal.UISynchronizer;

public final class DisplayAccess {

	public static void accessDisplayDuringStartup() {
		UISynchronizer.overrideThread.set(Boolean.TRUE);
	}
}
