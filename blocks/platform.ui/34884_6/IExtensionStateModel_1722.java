package org.eclipse.ui.navigator;


public interface IExtensionActivationListener {
	void onExtensionActivation(String aViewerId,
			String[] theNavigatorExtensionIds, boolean isActive);
}
