
package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IExtensionStateModel;
import org.eclipse.ui.navigator.INavigatorContentService;

public class CommonExtensionSite {

	private final INavigatorContentService contentService;

	private IExtensionStateModel extensionStateModel;

	protected CommonExtensionSite(INavigatorContentService aContentService,
			String anExtensionId) {

		Assert.isNotNull(aContentService);

		contentService = aContentService;
		if (anExtensionId != null) {
			extensionStateModel = aContentService.findStateModel(anExtensionId);
		}
	}

	public final INavigatorContentService getContentService() {
		return contentService;
	}

	public final IExtensionStateModel getExtensionStateModel() {
		return extensionStateModel;
	}

}
