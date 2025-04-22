package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.StructuredViewer;

public interface ICommonActionExtensionSite {

	IExtensionStateModel getExtensionStateModel();

	String getExtensionId();

	String getPluginId();

	INavigatorContentService getContentService();

	StructuredViewer getStructuredViewer();

	ICommonViewerSite getViewSite();
}
