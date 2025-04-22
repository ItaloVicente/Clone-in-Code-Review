
package org.eclipse.ui.tests.api;

import org.eclipse.ui.internal.services.INestable;

public class DummyService implements INestable {

	private boolean active;


	public boolean isActive() {
		return active;
	}
	
	@Override
	public void activate() {
		active = true;
	}

	
	@Override
	public void deactivate() {
		active = false;
	}

}
