
package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class GotoActionProvider extends CommonActionProvider {

	private GotoResourceAction gotoAction;

	@Override
	public void init(ICommonActionExtensionSite anActionSite) {
		gotoAction = new GotoResourceAction(anActionSite.getViewSite().getShell(), (CommonViewer)anActionSite.getStructuredViewer());
	}


	@Override
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(
				IWorkbenchActionConstants.GO_TO_RESOURCE, gotoAction);
		updateActionBars();
	}

}
