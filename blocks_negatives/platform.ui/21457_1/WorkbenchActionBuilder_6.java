     * Adds the perspective actions to the specified menu.
     */
    private void addPerspectiveActions(MenuManager menu) {
        {
            String openText = IDEWorkbenchMessages.Workbench_openPerspective;
            MenuManager changePerspMenuMgr = new MenuManager(openText,
            IContributionItem changePerspMenuItem = ContributionItemFactory.PERSPECTIVES_SHORTLIST
                    .create(getWindow());
            changePerspMenuMgr.add(changePerspMenuItem);
            menu.add(changePerspMenuMgr);
        }
        {
            MenuManager showViewMenuMgr = new MenuManager(IDEWorkbenchMessages.Workbench_showView, "showView"); //$NON-NLS-1$
            IContributionItem showViewMenu = ContributionItemFactory.VIEWS_SHORTLIST
                    .create(getWindow());
            showViewMenuMgr.add(showViewMenu);
            menu.add(showViewMenuMgr);
        }
        menu.add(new Separator());
        menu.add(editActionSetAction);
        menu.add(getSavePerspectiveItem());
        menu.add(getResetPerspectiveItem());
        menu.add(closePerspAction);
        menu.add(closeAllPerspsAction);
    }

    /**
     * Adds the keyboard navigation submenu to the specified menu.
     */
    private void addWorkingSetBuildActions(MenuManager menu) {
        buildWorkingSetMenu = new MenuManager(IDEWorkbenchMessages.Workbench_buildSet);
        IContributionItem workingSetBuilds = new BuildSetMenu(window,
                getActionBarConfigurer());
        buildWorkingSetMenu.add(workingSetBuilds);
        menu.add(buildWorkingSetMenu);
    }

    /**
     * Adds the keyboard navigation submenu to the specified menu.
     */
    private void addKeyboardShortcuts(MenuManager menu) {
        MenuManager subMenu = new MenuManager(IDEWorkbenchMessages.Workbench_shortcuts, "shortcuts"); //$NON-NLS-1$
        menu.add(subMenu);
        subMenu.add(showPartPaneMenuAction);
        subMenu.add(showViewMenuAction);
        subMenu.add(quickAccessAction);
        subMenu.add(new Separator());
        subMenu.add(maximizePartAction);
        subMenu.add(minimizePartAction);
        subMenu.add(new Separator());
        subMenu.add(activateEditorAction);
        subMenu.add(nextEditorAction);
        subMenu.add(prevEditorAction);
        subMenu.add(switchToEditorAction);
        subMenu.add(new Separator());
        subMenu.add(nextPartAction);
        subMenu.add(prevPartAction);
        subMenu.add(new Separator());
        subMenu.add(nextPerspectiveAction);
        subMenu.add(prevPerspectiveAction);
    }

    /**
