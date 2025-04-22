		IStructuredSelection selection = (IStructuredSelection) s;
		ICommonViewerWorkbenchSite site = (ICommonViewerWorkbenchSite) getActionSite()
				.getViewSite();
		if (shouldAddShowInMenu(selection)) {
			MenuManager showInSubMenu = UIUtils.createShowInMenu(site
					.getWorkbenchWindow());
			menu.appendToGroup(ICommonMenuConstants.GROUP_SHOW, showInSubMenu);
		}
