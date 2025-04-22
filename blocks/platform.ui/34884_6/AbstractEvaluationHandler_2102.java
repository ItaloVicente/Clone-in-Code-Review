
package org.eclipse.ui.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.HandlerEvent;

public abstract class AbstractEnabledHandler extends AbstractHandler {

	private boolean enabled = true;

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	protected void setEnabled(boolean isEnabled) {
		if (enabled != isEnabled) {
			enabled = isEnabled;
			fireHandlerChanged(new HandlerEvent(this, true, false));
		}
	}
}
