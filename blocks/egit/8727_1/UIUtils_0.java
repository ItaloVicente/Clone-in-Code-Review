	public static MenuManager createShowInMenu(IWorkbenchWindow workbenchWindow) {
		MenuManager showInSubMenu = new MenuManager(getShowInMenuLabel());
		showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN.create(workbenchWindow));
		return showInSubMenu;
	}

	private static String getShowInMenuLabel() {
		IBindingService bindingService = (IBindingService) PlatformUI.getWorkbench().getAdapter(IBindingService.class);
		if (bindingService != null) {
			String keyBinding = bindingService.getBestActiveBindingFormattedFor(IWorkbenchCommandConstants.NAVIGATE_SHOW_IN_QUICK_MENU);
			if (keyBinding != null)
				return UIText.UIUtils_ShowInMenuLabel + '\t' + keyBinding;
		}

		return UIText.UIUtils_ShowInMenuLabel;
	}
