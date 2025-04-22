
package org.eclipse.ui.internal.services;

import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.services.IServiceScopes;

public interface IWorkbenchLocationService {
	public String getServiceScope();

	public int getServiceLevel();

	public IWorkbench getWorkbench();

	public IWorkbenchWindow getWorkbenchWindow();

	public IWorkbenchPartSite getPartSite();

	public IEditorSite getMultiPageEditorSite();

	public IPageSite getPageSite();
}
