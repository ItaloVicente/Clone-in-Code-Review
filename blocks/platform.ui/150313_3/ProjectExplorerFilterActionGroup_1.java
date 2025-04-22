package org.eclipse.ui.internal.navigator.resources;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.internal.navigator.filters.FilterActionGroup;
import org.eclipse.ui.internal.navigator.filters.SelectFiltersAction;
import org.eclipse.ui.navigator.CommonViewer;

public class ProjectExplorerFilterActionGroup extends FilterActionGroup {

	private SelectFiltersAction selectFiltersAction;
	private CommonViewer commonViewer;

	public ProjectExplorerFilterActionGroup(CommonViewer aCommonViewer) {
		super(aCommonViewer);
		commonViewer = aCommonViewer;
		makeActions();
	}

	public void makeActions() {
		selectFiltersAction = new SelectFiltersAction(commonViewer, this);
		String imageFilePath = "icons/full/elcl16/filter_ps.png"; //$NON-NLS-1$
		ResourceLocator.imageDescriptorFromBundle(getClass(), imageFilePath).ifPresent(d -> {
			selectFiltersAction.setImageDescriptor(d);
			selectFiltersAction.setHoverImageDescriptor(d);
		});
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		super.fillActionBars(actionBars);
	}

	protected void fillToolbar(IToolBarManager toolBar) {
		toolBar.add(selectFiltersAction);
	}
}
