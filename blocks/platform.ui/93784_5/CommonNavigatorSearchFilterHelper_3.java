package org.eclipse.ui.internal.navigator.filters.search;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.navigator.CommonNavigatorActionGroup;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

public class CommonNavigatorSearchFilterAction extends Action {

	private CommonViewer commonViewer;

	public CommonNavigatorSearchFilterAction(CommonViewer commonViewer) {
		super(ActionFactory.FIND.getId(), AS_CHECK_BOX);
		this.commonViewer = commonViewer;
	}

	@Override
	public void run() {
		CommonNavigatorSearchFilterHelper.getInstance().toggleFilter(commonViewer);
	}
}
