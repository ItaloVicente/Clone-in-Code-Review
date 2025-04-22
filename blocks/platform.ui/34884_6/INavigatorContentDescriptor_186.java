
package org.eclipse.ui.navigator;

public interface INavigatorActivationService {

	public INavigatorContentDescriptor[] activateExtensions(
			String[] extensionIds, boolean toDeactivateAllOthers);

	public INavigatorContentDescriptor[] deactivateExtensions(
			String[] extensionIds, boolean toActivateAllOthers);

	public boolean isNavigatorExtensionActive(String aNavigatorExtensionId);

	public void persistExtensionActivations();

	public void addExtensionActivationListener(
			IExtensionActivationListener aListener);

	public void removeExtensionActivationListener(
			IExtensionActivationListener aListener);
}
