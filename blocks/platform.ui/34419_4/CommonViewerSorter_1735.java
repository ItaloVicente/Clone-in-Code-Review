package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.internal.navigator.CommonViewerSiteDelegate;
import org.eclipse.ui.internal.navigator.CommonViewerSiteIEditorPartSiteDelegate;
import org.eclipse.ui.internal.navigator.CommonViewerSiteIPageSiteDelegate;
import org.eclipse.ui.internal.navigator.CommonViewerSiteIViewSiteDelegate;
import org.eclipse.ui.part.IPageSite;

public final class CommonViewerSiteFactory {
	public static ICommonViewerWorkbenchSite createCommonViewerSite(
			IViewSite aViewSite) {
		return new CommonViewerSiteIViewSiteDelegate(aViewSite);
	}

	public static ICommonViewerWorkbenchSite createCommonViewerSite(
			IEditorSite aEditorSite) {
		return new CommonViewerSiteIEditorPartSiteDelegate(aEditorSite);
	}

	public static ICommonViewerSite createCommonViewerSite(String anId,
			ISelectionProvider aSelectionProvider, Shell aShell) {
		return new CommonViewerSiteDelegate(anId, aSelectionProvider, aShell);
	}

	public static ICommonViewerSite createCommonViewerSite(String anId,
			IPageSite aPageSite) {
		return new CommonViewerSiteIPageSiteDelegate(anId, aPageSite);
	}

}
