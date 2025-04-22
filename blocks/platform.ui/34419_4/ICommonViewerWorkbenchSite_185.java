package org.eclipse.ui.navigator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

public interface ICommonViewerSite extends IAdaptable {

	String getId();


	Shell getShell();

	ISelectionProvider getSelectionProvider();

	public void setSelectionProvider(ISelectionProvider provider);
	
}
