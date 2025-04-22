
package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.internal.navigator.CommonNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorContentService;

public class UpdateActiveFiltersOperation extends AbstractOperation {

	private String[] filterIdsToActivate; 

	private final CommonViewer commonViewer;

	private final INavigatorContentService contentService;

	public UpdateActiveFiltersOperation(CommonViewer aCommonViewer,
			String[] theActiveFilterIds) {
		super(
				CommonNavigatorMessages.UpdateFiltersOperation_Update_CommonViewer_Filter_);
		Assert.isNotNull(theActiveFilterIds);
		
		commonViewer = aCommonViewer;
		contentService = commonViewer.getNavigatorContentService();
		filterIdsToActivate = theActiveFilterIds;

	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) {
		contentService.getFilterService().activateFilterIdsAndUpdateViewer(filterIdsToActivate);
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
