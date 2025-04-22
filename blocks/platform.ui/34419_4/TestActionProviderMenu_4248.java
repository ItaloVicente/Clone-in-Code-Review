package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class TestActionProviderFiltered extends CommonActionProvider {

	private IAction action;

	public TestActionProviderFiltered() {

	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);

		action = new Action() {

		};
		action.setId(site.getExtensionId());
		action.setText(site.getExtensionId());
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (!filterAction(action)) {
			menu.add(action);
		}
	}

}
