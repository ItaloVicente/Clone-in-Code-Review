package org.eclipse.ui.internal.navigator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.ICommonViewerSite;

public class CommonViewerSiteDelegate implements ICommonViewerSite {
	
	
	private String id; 
	private ISelectionProvider selectionProvider; 
	private Shell shell;

	public CommonViewerSiteDelegate(String anId,  ISelectionProvider aSelectionProvider, Shell aShell) {
		Assert.isNotNull(anId);
		Assert.isNotNull(aSelectionProvider);
		Assert.isNotNull(aShell);
		id = anId;
		selectionProvider = aSelectionProvider;		
		shell = aShell;
	} 

	@Override
	public String getId() {
		return id;
	} 

	@Override
	public Shell getShell() {
		return shell;
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}  


	@Override
	public void setSelectionProvider(ISelectionProvider aSelectionProvider) {
		selectionProvider = aSelectionProvider;
	}

	@Override
	public Object getAdapter(Class adapter) { 
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

}
