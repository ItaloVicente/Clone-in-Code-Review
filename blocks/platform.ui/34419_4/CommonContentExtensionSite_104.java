package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerSite;

public final class CommonActionExtensionSite extends CommonExtensionSite
		implements ICommonActionExtensionSite {

	private String extensionId;

	private String pluginId;
	
	private ICommonViewerSite commonViewerSite;

	private StructuredViewer structuredViewer;

	public CommonActionExtensionSite(String anExtensionId,
			String aPluginId,
			ICommonViewerSite aCommonViewerSite,
			NavigatorContentService aContentService,
			StructuredViewer aStructuredViewer) {
		super(aContentService, anExtensionId); 

		Assert.isNotNull(aCommonViewerSite);
		Assert.isNotNull(aStructuredViewer);
		extensionId = anExtensionId;
		pluginId = aPluginId;
		commonViewerSite = aCommonViewerSite;
		structuredViewer = aStructuredViewer;

	}

	@Override
	public String getExtensionId() {
		return extensionId;
	}

	@Override
	public String getPluginId() {
		return pluginId;
	}

	@Override
	public StructuredViewer getStructuredViewer() {
		return structuredViewer;
	}

	@Override
	public ICommonViewerSite getViewSite() {
		return commonViewerSite;
	}
}
