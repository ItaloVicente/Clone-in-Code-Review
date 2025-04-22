
package org.eclipse.ui.internal.navigator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.internal.navigator.extensions.ExtensionStateModel;
import org.eclipse.ui.navigator.IExtensionStateModel;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentService;

public class NavigatorExtensionStateService {

	private final Object lock = new Object();
	private INavigatorContentService contentService;

	public NavigatorExtensionStateService(INavigatorContentService theContentService) {
		contentService = theContentService;
	}

	private final Map/* <INavigatorContentDescriptor, IExtensionStateModel> */stateModels = new HashMap();

	public IExtensionStateModel getExtensionStateModel(
			INavigatorContentDescriptor aDescriptor) {
		synchronized (lock) {
			IExtensionStateModel model = (IExtensionStateModel) stateModels
					.get(aDescriptor);
			if (model == null)
				stateModels.put(aDescriptor, model = new ExtensionStateModel(
						aDescriptor.getId(), contentService.getViewerId()));
			return model;
		}
	}

}
