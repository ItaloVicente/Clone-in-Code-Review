		MenuManager submenu = new MenuManager(ResourceNavigatorMessages.ResourceNavigator_openWith, OPEN_WITH_ID);
		submenu.add(new OpenWithMenu(navigator.getSite().getPage(), (IFile) element));
		menu.add(submenu);
	}

	private void addNewWindowAction(IMenuManager menu, IStructuredSelection selection) {

		if (selection.size() != 1) {
