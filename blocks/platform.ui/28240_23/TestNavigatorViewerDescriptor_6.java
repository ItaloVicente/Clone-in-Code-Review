package org.eclipse.ui.tests.navigator.util;

import org.eclipse.ui.navigator.IExtensionActivationListener;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;

public class TestNavigatorActivationService implements INavigatorActivationService {
	@Override
	public INavigatorContentDescriptor[] activateExtensions(String[] extensionIds,
			boolean toDeactivateAllOthers) {
		return null;
	}

	@Override
	public INavigatorContentDescriptor[] deactivateExtensions(String[] extensionIds,
			boolean toActivateAllOthers) {
		return null;
	}

	@Override
	public boolean isNavigatorExtensionActive(String aNavigatorExtensionId) {
		return false;
	}

	@Override
	public void persistExtensionActivations() {
	}

	@Override
	public void addExtensionActivationListener(IExtensionActivationListener aListener) {
	}

	@Override
	public void removeExtensionActivationListener(IExtensionActivationListener aListener) {
	}
}
