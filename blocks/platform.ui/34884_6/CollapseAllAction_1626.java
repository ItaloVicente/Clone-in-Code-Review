
package org.eclipse.ui.internal.navigator;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.ui.navigator.IExtensionActivationListener;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.INavigatorViewerDescriptor;

public class VisibilityAssistant implements IExtensionActivationListener {

	private final INavigatorViewerDescriptor viewerDescriptor;

	private final Set programmaticVisibilityBindings = new HashSet();

	private final Set programmaticRootBindings = new HashSet();

	private final ListenerList listeners = new ListenerList();

	private final INavigatorActivationService activationService;

	public interface VisibilityListener {

		void onVisibilityOrActivationChange();
	}

	public VisibilityAssistant(INavigatorViewerDescriptor aViewerDescriptor,
			INavigatorActivationService anActivationService) {
		Assert.isNotNull(aViewerDescriptor);
		viewerDescriptor = aViewerDescriptor;

		activationService = anActivationService;
		activationService.addExtensionActivationListener(this);
	}

	public void dispose() {
		activationService.removeExtensionActivationListener(this);
	}

	public void bindExtensions(String[] theExtensions, boolean isRoot) {
		if (theExtensions == null) {
			return;
		}
		for (int i = 0; i < theExtensions.length; i++) {
			programmaticVisibilityBindings.add(theExtensions[i]);
			if (isRoot) {
				programmaticRootBindings.add(theExtensions[i]);
			}
		}
		notifyClients();
	}

	public void addListener(VisibilityListener aListener) {
		listeners.add(aListener);
	}

	public void removeListener(VisibilityListener aListener) {
		listeners.remove(aListener);
	}

	private void notifyClients() {
		Object[] clients = listeners.getListeners();
		for (int i = 0; i < clients.length; i++) {
			((VisibilityListener) clients[i]).onVisibilityOrActivationChange();
		}
	}

	public boolean isVisibleAndActive(
			INavigatorContentDescriptor aContentDescriptor) {
		return isActive(aContentDescriptor) && isVisible(aContentDescriptor);
	}

	public boolean isActive(INavigatorContentDescriptor aContentDescriptor) {
		return activationService.isNavigatorExtensionActive(aContentDescriptor
				.getId());
	}

	public boolean isActive(String aContentExtensionId) {
		return activationService
				.isNavigatorExtensionActive(aContentExtensionId);
	}

	public boolean isVisible(INavigatorContentDescriptor aContentDescriptor) {
		return programmaticVisibilityBindings.contains(aContentDescriptor
				.getId())
				|| viewerDescriptor
						.isVisibleContentExtension(aContentDescriptor.getId());
	}

	public boolean isVisible(String aContentExtensionId) {
		return programmaticVisibilityBindings.contains(aContentExtensionId)
				|| viewerDescriptor
						.isVisibleContentExtension(aContentExtensionId);
	}

	public boolean isRootExtension(String aContentExtensionId) {
		return programmaticRootBindings.contains(aContentExtensionId)
				|| viewerDescriptor.isRootExtension(aContentExtensionId);
	}

	@Override
	public void onExtensionActivation(String aViewerId,
			String[] theNavigatorExtensionIds, boolean isActive) {
		if (aViewerId.equals(viewerDescriptor.getViewerId())) {
			notifyClients();
		}

	}

}
