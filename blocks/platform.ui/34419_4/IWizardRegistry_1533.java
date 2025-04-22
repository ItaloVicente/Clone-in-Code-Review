package org.eclipse.ui.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPartDescriptor;
import org.eclipse.ui.IWorkbenchWizard;

public interface IWizardDescriptor extends IWorkbenchPartDescriptor, IAdaptable {

	IStructuredSelection adaptedSelection(IStructuredSelection selection);

	String getDescription();
	
	String [] getTags();
	
	IWorkbenchWizard createWizard() throws CoreException;
	
	public ImageDescriptor getDescriptionImage();

	String getHelpHref();	

	IWizardCategory getCategory();
	
	boolean canFinishEarly();

	boolean hasPages();
}
