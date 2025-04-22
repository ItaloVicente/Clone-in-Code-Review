        MenuManager submenu = new MenuManager(ResourceNavigatorMessages.ResourceNavigator_openWith, OPEN_WITH_ID);
        submenu.add(new OpenWithMenu(navigator.getSite().getPage(),
                (IFile) element));
        menu.add(submenu);
    }

    /**
     * Adds the Open in New Window action to the context menu.
     *
     * @param menu the context menu
     * @param selection the current selection
     */
    private void addNewWindowAction(IMenuManager menu,
            IStructuredSelection selection) {

        if (selection.size() != 1) {
