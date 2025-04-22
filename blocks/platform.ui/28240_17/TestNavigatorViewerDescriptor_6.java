
package org.eclipse.ui.tests.navigator.util;

import org.eclipse.ui.navigator.IExtensionActivationListener;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;

public class TestNavigatorActivationService implements INavigatorActivationService {

	public INavigatorContentDescriptor[] activateExtensions(String[] extensionIds,
			boolean toDeactivateAllOthers) {
		return null;
	}

	public INavigatorContentDescriptor[] deactivateExtensions(String[] extensionIds,
			boolean toActivateAllOthers) {
		return null;
	}

	public boolean isNavigatorExtensionActive(String aNavigatorExtensionId) {
		return false;
	}

	public void persistExtensionActivations() {
	}

	public void addExtensionActivationListener(IExtensionActivationListener aListener) {
	}

	public void removeExtensionActivationListener(IExtensionActivationListener aListener) {
	}

}
