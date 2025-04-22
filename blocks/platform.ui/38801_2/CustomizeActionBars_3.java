package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.internal.provisional.action.IToolBarContributionItem;
import org.eclipse.jface.internal.provisional.action.ToolBarContributionItem2;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.internal.provisional.application.IActionBarConfigurer2;
import org.eclipse.ui.services.IServiceLocator;

public class CustomizeActionBars implements IActionBarConfigurer2,
		IActionBars2 {

	IWorkbenchWindowConfigurer configurer;

	MenuManager menuManager = new MenuManager();
	CoolBarManager coolBarManager = new CoolBarManager();
	private StatusLineManager statusLineManager = new StatusLineManager();

	public CustomizeActionBars(IWorkbenchWindowConfigurer configurer) {
		this.configurer = configurer;
	}

	@Override
	public IWorkbenchWindowConfigurer getWindowConfigurer() {
		return configurer;
	}

	@Override
	public IMenuManager getMenuManager() {
		return menuManager;
	}

	@Override
	public IStatusLineManager getStatusLineManager() {
		return statusLineManager;
	}

	@Override
	public ICoolBarManager getCoolBarManager() {
		return coolBarManager;
	}

	@Override
	public IToolBarManager getToolBarManager() {
		return null;
	}

	@Override
	public void setGlobalActionHandler(String actionID, IAction handler) {
	}

	@Override
	public void updateActionBars() {
	}

	@Override
	public void clearGlobalActionHandlers() {
	}

	@Override
	public IAction getGlobalActionHandler(String actionId) {
		return null;
	}

	@Override
	public void registerGlobalAction(IAction action) {
	}

	public void dispose() {
		coolBarManager.dispose();
		menuManager.dispose();
		statusLineManager.dispose();
	}

	@Override
	public final IServiceLocator getServiceLocator() {
		return configurer.getWindow();
	}

	@Override
	public IToolBarContributionItem createToolBarContributionItem(
			IToolBarManager toolBarManager, String id) {
		return new ToolBarContributionItem2(toolBarManager, id);
	}

	@Override
	public IToolBarManager createToolBarManager() {
		return new ToolBarManager();
	}
}
