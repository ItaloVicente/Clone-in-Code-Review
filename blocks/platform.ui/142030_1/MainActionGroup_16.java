		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		MenuManager newMenu = new MenuManager(ResourceNavigatorMessages.ResourceNavigator_new);
		menu.add(newMenu);
		newMenu.add(newWizardMenu);

		gotoGroup.fillContextMenu(menu);
		openGroup.fillContextMenu(menu);
		menu.add(new Separator());

		refactorGroup.fillContextMenu(menu);
		menu.add(new Separator());

		menu.add(importAction);
		menu.add(exportAction);
		importAction.selectionChanged(selection);
		exportAction.selectionChanged(selection);
		menu.add(new Separator());

		workspaceGroup.fillContextMenu(menu);

		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS + "-end")); //$NON-NLS-1$
		menu.add(new Separator());

		if (selection.size() == 1) {
			propertyDialogAction.selectionChanged(selection);
			menu.add(propertyDialogAction);
		}
	}

	@Override
