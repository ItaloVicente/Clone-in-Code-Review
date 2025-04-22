package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

public class ShowInActionProvider extends CommonActionProvider {

	@Override
	public void fillContextMenu(IMenuManager menu) {
		ICommonViewerSite viewerSite = getActionSite().getViewSite();
		if (viewerSite instanceof ICommonViewerWorkbenchSite) {
			IContributionItem showInAction = ContributionItemFactory.VIEWS_SHOW_IN
					.create(((ICommonViewerWorkbenchSite) viewerSite).getWorkbenchWindow());
			MenuManager showInSubMenu = new MenuManager(getShowInMenuLabel());
			showInSubMenu.add(showInAction);
			menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN, showInSubMenu);
		}
	}

	private String getShowInMenuLabel() {
		IBindingService bindingService = PlatformUI.getWorkbench().getAdapter(IBindingService.class);
		String keyBinding = bindingService != null
				? bindingService
						.getBestActiveBindingFormattedFor(IWorkbenchCommandConstants.NAVIGATE_SHOW_IN_QUICK_MENU)
				: ""; //$NON-NLS-1$
		return WorkbenchNavigatorMessages.ShowInActionProvider_showInAction_label + '\t' + keyBinding;
	}
}
