package org.eclipse.ui.tests.navigator.extension;


import org.junit.Assert;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;

import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class TestActionProviderDependent extends CommonActionProvider {

	private IAction action = null;

	@Override
	public void init(ICommonActionExtensionSite aConfig) {
		 action = new TestActionDependent(aConfig.getViewSite().getShell(), aConfig.getExtensionId());
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IMenuManager submenu = menu.findMenuUsingPath(TestActionProviderMenu.GROUP_TEST_MENU);
		Assert.assertNotNull("The submenu should have been added by TestActionProviderMenu!", submenu);
		submenu.insertAfter(TestActionProviderMenu.GROUP_TEST_DEPENDENCY, action);
	}

}
