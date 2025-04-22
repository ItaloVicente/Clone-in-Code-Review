        actionBars.setGlobalActionHandler(ActionFactory.PROPERTIES.getId(),
                propertyDialogAction);
        actionBars.setGlobalActionHandler(IDEActionFactory.BOOKMARK.getId(),
                addBookmarkAction);
        actionBars.setGlobalActionHandler(IDEActionFactory.ADD_TASK.getId(),
                addTaskAction);

        gotoGroup.fillActionBars(actionBars);
        openGroup.fillActionBars(actionBars);
        refactorGroup.fillActionBars(actionBars);
        workingSetGroup.fillActionBars(actionBars);
        sortAndFilterGroup.fillActionBars(actionBars);
        workspaceGroup.fillActionBars(actionBars);
        undoRedoGroup.fillActionBars(actionBars);

        IMenuManager menu = actionBars.getMenuManager();
        menu.add(toggleLinkingAction);

        IToolBarManager toolBar = actionBars.getToolBarManager();
        toolBar.add(new Separator());
        toolBar.add(collapseAllAction);
        toolBar.add(toggleLinkingAction);
    }

    /**
     * Updates the actions which were added to the action bars,
     * delegating to the subgroups as necessary.
     */
    @Override
