package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.internal.navigator.CommonNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;

public class SelectFiltersAction extends Action {

	private final CommonViewer commonViewer;
	private FilterActionGroup filterGroup; 

	public SelectFiltersAction(CommonViewer aCommonViewer, FilterActionGroup aFilterGroup) {
		super(CommonNavigatorMessages.SelectFiltersActionDelegate_0); 
		setToolTipText(CommonNavigatorMessages.SelectFiltersActionDelegate_1); 
		commonViewer = aCommonViewer; 
		filterGroup = aFilterGroup;
	}

	@Override
	public void run() {
		CommonFilterSelectionDialog filterSelectionDialog = new CommonFilterSelectionDialog(commonViewer);
		filterSelectionDialog.open();
		filterGroup.updateFilterShortcuts();
	}

}
