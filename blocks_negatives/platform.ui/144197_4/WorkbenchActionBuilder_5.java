		{
			MenuManager showInSubMenu = new MenuManager(
					IDEWorkbenchMessages.Workbench_showIn, "showIn"); //$NON-NLS-1$
			showInSubMenu.setActionDefinitionId(showInQuickMenu
					.getActionDefinitionId());
			showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN
					.create(getWindow()));
			menu.add(showInSubMenu);
		}
