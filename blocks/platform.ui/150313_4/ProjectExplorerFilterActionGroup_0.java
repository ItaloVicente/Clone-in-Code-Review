package org.eclipse.ui.internal.navigator.resources;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.internal.navigator.CommonNavigatorActionGroup;
import org.eclipse.ui.internal.navigator.filters.FilterActionGroup;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.LinkHelperService;

public class ProjectExplorerActionGroup extends CommonNavigatorActionGroup {

	private ProjectExplorerFilterActionGroup fFilterActionGroup;

	public ProjectExplorerActionGroup(CommonNavigator aNavigator, CommonViewer aViewer,
			LinkHelperService linkHelperService) {
		super(aNavigator, aViewer, linkHelperService);
	}

	@Override
	protected FilterActionGroup createFilterActionGroup(CommonViewer pCommonViewer) {
		fFilterActionGroup = new ProjectExplorerFilterActionGroup(pCommonViewer);
		return fFilterActionGroup;
	}

	@Override
	protected void fillToolBar(IToolBarManager pToolBar) {
		super.fillToolBar(pToolBar);
		fFilterActionGroup.fillToolbar(pToolBar);
	}
}
