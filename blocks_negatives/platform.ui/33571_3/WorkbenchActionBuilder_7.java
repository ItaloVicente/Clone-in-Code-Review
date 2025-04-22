        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createNavigateMenu());
        menuBar.add(createProjectMenu());
        menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(createWindowMenu());
        menuBar.add(createHelpMenu());
    }

    /**
     * Creates and returns the File menu.
     */
    private MenuManager createFileMenu() {
        MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_file, IWorkbenchActionConstants.M_FILE);
        menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_START));
        {
            String newText = IDEWorkbenchMessages.Workbench_new;
            String newId = ActionFactory.NEW.getId();
            MenuManager newMenu = new MenuManager(newText, newId);
            newMenu.add(new Separator(newId));
            this.newWizardMenu = new NewWizardMenu(getWindow());
            newMenu.add(this.newWizardMenu);
            newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
            menu.add(newMenu);
        }
		
        menu.add(new GroupMarker(IWorkbenchActionConstants.NEW_EXT));
        menu.add(new Separator());

        menu.add(closeAction);
        menu.add(closeAllAction);
        menu.add(new GroupMarker(IWorkbenchActionConstants.CLOSE_EXT));
        menu.add(new Separator());
        menu.add(saveAction);
        menu.add(saveAsAction);
        menu.add(saveAllAction);
        menu.add(getRevertItem());
        menu.add(new Separator());
        menu.add(getMoveItem());
        menu.add(getRenameItem());
        menu.add(getRefreshItem());

        menu.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));
        menu.add(new Separator());
        menu.add(getPrintItem());
        menu.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));
        menu.add(new Separator());
        menu.add(openWorkspaceAction);
        menu.add(new GroupMarker(IWorkbenchActionConstants.OPEN_EXT));
        menu.add(new Separator());
        menu.add(importResourcesAction);
        menu.add(exportResourcesAction);
        menu.add(new GroupMarker(IWorkbenchActionConstants.IMPORT_EXT));
        menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        menu.add(new Separator());
        menu.add(getPropertiesItem());

        menu.add(ContributionItemFactory.REOPEN_EDITORS.create(getWindow()));
        menu.add(new GroupMarker(IWorkbenchActionConstants.MRU));
        menu.add(new Separator());
        
