
package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.INavigatorContentExtension;
import org.eclipse.ui.navigator.INavigatorContentService;

public class CommonContentExtensionSite extends CommonExtensionSite implements
		ICommonContentExtensionSite {

	private NavigatorContentExtension extension;

	private IMemento memento;

	private NavigatorContentService contentService;

	public CommonContentExtensionSite(String anExtensionId,
			NavigatorContentService aContentService, IMemento aMemento) {
		super(aContentService, anExtensionId);

		NavigatorContentDescriptor contentDescriptor = NavigatorContentDescriptorManager
				.getInstance().getContentDescriptor(anExtensionId);

		extension = aContentService.getExtension(contentDescriptor);
		memento = aMemento;
		contentService = aContentService;
	}

	@Override
	public IMemento getMemento() {
		return memento;
	}

	@Override
	public INavigatorContentExtension getExtension() {
		return extension;
	}

	@Override
	public INavigatorContentService getService() { 
		return contentService;
	}

}
