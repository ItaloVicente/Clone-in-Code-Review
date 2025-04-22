        coolBar.add(new GroupMarker(IIDEActionConstants.GROUP_EDIT));
        { // Edit group
            IToolBarManager editToolBar = actionBarConfigurer.createToolBarManager();
            editToolBar.add(new Separator(IWorkbenchActionConstants.EDIT_GROUP));
            editToolBar.add(undoAction);
            editToolBar.add(redoAction);

            coolBar.add(actionBarConfigurer.createToolBarContributionItem(editToolBar,
                    IWorkbenchActionConstants.TOOLBAR_EDIT));
        }

