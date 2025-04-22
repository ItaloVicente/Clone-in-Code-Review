
package org.eclipse.ui.internal.navigator;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.part.IPageSite;

public class CommonViewerSiteIPageSiteDelegate implements ICommonViewerSite {

	private IPageSite pageSite;

	private String viewerId;

	public CommonViewerSiteIPageSiteDelegate(String aViewerId,
			IPageSite aPageSite) {
		viewerId = aViewerId;
		pageSite = aPageSite;
	}

	@Override
	public String getId() {
		return viewerId;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return pageSite.getAdapter(adapter);
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return pageSite.getSelectionProvider();
	}

	@Override
	public void setSelectionProvider(ISelectionProvider aSelectionProvider) {
		pageSite.setSelectionProvider(aSelectionProvider);
	}

	@Override
	public Shell getShell() {
		return pageSite.getShell();
	}

}
