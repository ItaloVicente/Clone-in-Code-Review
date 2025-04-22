
package org.eclipse.ui.internal.navigator.filters;

import java.util.Arrays;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.internal.navigator.CommonNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorContentDescriptor;
import org.eclipse.ui.navigator.INavigatorContentService;

public class UpdateActiveExtensionsOperation extends AbstractOperation {

	private String[] contentExtensionsToActivate;

	private final CommonViewer commonViewer;

	private final INavigatorContentService contentService;

	public UpdateActiveExtensionsOperation(CommonViewer aCommonViewer,
			String[] theExtensionsToActivate) {
		super(
				CommonNavigatorMessages.UpdateFiltersOperation_Update_CommonViewer_Filter_);
		commonViewer = aCommonViewer;
		contentService = commonViewer.getNavigatorContentService();
		contentExtensionsToActivate = theExtensionsToActivate;

	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) {

		boolean updateExtensionActivation = false;

		Arrays.sort(contentExtensionsToActivate);
		
		IStructuredSelection ssel = null;
	
		try {
			commonViewer.getControl().setRedraw(false);
			
			ISelection selection = commonViewer.getSelection();
			if(selection instanceof IStructuredSelection)
				ssel = (IStructuredSelection) selection;

			INavigatorContentDescriptor[] visibleContentDescriptors = contentService
					.getVisibleExtensions();

			int indexofContentExtensionIdToBeActivated;
			for (int i = 0; i < visibleContentDescriptors.length
					&& !updateExtensionActivation; i++) {
				indexofContentExtensionIdToBeActivated = Arrays.binarySearch(
						contentExtensionsToActivate,
						visibleContentDescriptors[i].getId());
				if (indexofContentExtensionIdToBeActivated >= 0
						^ contentService.isActive(visibleContentDescriptors[i]
								.getId())) {
					updateExtensionActivation = true;
				}
			}

			if (updateExtensionActivation) {
				 
				contentService.getActivationService().activateExtensions(
						contentExtensionsToActivate, true);
				contentService.getActivationService()
						.persistExtensionActivations();
				

				Object[] expandedElements = commonViewer.getExpandedElements();

				contentService.update();

				commonViewer.refresh();
				
				Object[] originalObjects = ssel.toArray(); 
				
				commonViewer.setExpandedElements(expandedElements);

				IStructuredSelection newSelection = new StructuredSelection(originalObjects);
				commonViewer.setSelection(newSelection, true); 				
			}

		} finally {
			commonViewer.getControl().setRedraw(true);
		} 

		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) {
		return null;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) {
		return null;
	}
}
