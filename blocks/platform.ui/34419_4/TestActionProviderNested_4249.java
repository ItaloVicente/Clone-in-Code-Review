package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class TestActionProviderMenu extends CommonActionProvider {

	public static final String GROUP_TEST_MENU = "group.testMenu";

	public static final String GROUP_TEST_DEPENDENCY = "group.testDependency";

	private IAction action = null;

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);
		action = new TestAction(aSite.getViewSite().getShell());
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IMenuManager submenu = new MenuManager("CN Test Menu", GROUP_TEST_MENU);
		submenu.add(action);
		submenu.add(new GroupMarker(GROUP_TEST_DEPENDENCY));
		menu.insertAfter(ICommonMenuConstants.GROUP_REORGANIZE, submenu);
	}


}
