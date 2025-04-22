    private MenuManager createEditMenu() {
        MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_edit, IWorkbenchActionConstants.M_EDIT);
        menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_START));

        menu.add(undoAction);
        menu.add(redoAction);
        menu.add(new GroupMarker(IWorkbenchActionConstants.UNDO_EXT));
        menu.add(new Separator());

        menu.add(getCutItem());
        menu.add(getCopyItem());
        menu.add(getPasteItem());
        menu.add(new GroupMarker(IWorkbenchActionConstants.CUT_EXT));
        menu.add(new Separator());

        menu.add(getDeleteItem());
        menu.add(getSelectAllItem());
        menu.add(new Separator());

        menu.add(getFindItem());
        menu.add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
        menu.add(new Separator());

        menu.add(getBookmarkItem());
        menu.add(getTaskItem());
        menu.add(new GroupMarker(IWorkbenchActionConstants.ADD_EXT));

        menu.add(new GroupMarker(IWorkbenchActionConstants.EDIT_END));
        menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        return menu;
    }

    /**
     * Creates and returns the Navigate menu.
     */
    private MenuManager createNavigateMenu() {
        MenuManager menu = new MenuManager(
                IDEWorkbenchMessages.Workbench_navigate, IWorkbenchActionConstants.M_NAVIGATE);
        menu.add(new GroupMarker(IWorkbenchActionConstants.NAV_START));
        menu.add(goIntoAction);

        MenuManager goToSubMenu = new MenuManager(IDEWorkbenchMessages.Workbench_goTo, IWorkbenchActionConstants.GO_TO);
        menu.add(goToSubMenu);
        goToSubMenu.add(backAction);
        goToSubMenu.add(forwardAction);
        goToSubMenu.add(upAction);
        goToSubMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        menu.add(new Separator(IWorkbenchActionConstants.OPEN_EXT));
        for (int i = 2; i < 5; ++i) {
            menu.add(new Separator(IWorkbenchActionConstants.OPEN_EXT + i));
        }
        menu.add(new Separator(IWorkbenchActionConstants.SHOW_EXT));
        {
			MenuManager showInSubMenu = new MenuManager(
					IDEWorkbenchMessages.Workbench_showIn, "showIn"); //$NON-NLS-1$
			showInSubMenu.setActionDefinitionId(showInQuickMenu
					.getActionDefinitionId());
			showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN
					.create(getWindow()));
