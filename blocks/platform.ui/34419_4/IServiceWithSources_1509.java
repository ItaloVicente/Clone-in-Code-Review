
package org.eclipse.ui.services;


public interface IServiceScopes {
	public static final String WORKBENCH_SCOPE = "org.eclipse.ui.services.IWorkbench"; //$NON-NLS-1$

	public static final String DIALOG_SCOPE = "org.eclipse.ui.services.IDialog"; //$NON-NLS-1$
	public static final String WINDOW_SCOPE = "org.eclipse.ui.IWorkbenchWindow"; //$NON-NLS-1$
	
	public static final String PARTSITE_SCOPE = "org.eclipse.ui.part.IWorkbenchPartSite"; //$NON-NLS-1$
	
	public static final String PAGESITE_SCOPE = "org.eclipse.ui.part.PageSite"; //$NON-NLS-1$
	
	public static final String MPESITE_SCOPE = "org.eclipse.ui.part.MultiPageEditorSite"; //$NON-NLS-1$
}
