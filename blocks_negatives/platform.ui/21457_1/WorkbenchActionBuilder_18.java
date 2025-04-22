        
        switchToEditorAction = ActionFactory.SHOW_OPEN_EDITORS
                .create(window);
        register(switchToEditorAction);

        workbookEditorsAction = ActionFactory.SHOW_WORKBOOK_EDITORS
        		.create(window);
        register(workbookEditorsAction);
        
        quickAccessAction = ActionFactory.SHOW_QUICK_ACCESS
        	.create(window);

        hideShowEditorAction = ActionFactory.SHOW_EDITOR.create(window);
        register(hideShowEditorAction);
        editActionSetAction = ActionFactory.EDIT_ACTION_SETS
                .create(window);
        register(editActionSetAction);
        lockToolBarAction = ActionFactory.LOCK_TOOL_BAR.create(window);
        register(lockToolBarAction);
        closePerspAction = ActionFactory.CLOSE_PERSPECTIVE.create(window);
        register(closePerspAction);
        closeAllPerspsAction = ActionFactory.CLOSE_ALL_PERSPECTIVES
                .create(window);
        register(closeAllPerspsAction);

        forwardHistoryAction = ActionFactory.FORWARD_HISTORY
                .create(window);
        register(forwardHistoryAction);

        backwardHistoryAction = ActionFactory.BACKWARD_HISTORY
                .create(window);
        register(backwardHistoryAction);




        quitAction = ActionFactory.QUIT.create(window);
        register(quitAction);



        goIntoAction = ActionFactory.GO_INTO.create(window);
        register(goIntoAction);

        backAction = ActionFactory.BACK.create(window);
        register(backAction);

        forwardAction = ActionFactory.FORWARD.create(window);
        register(forwardAction);

        upAction = ActionFactory.UP.create(window);
        register(upAction);

        nextAction = ActionFactory.NEXT.create(window);
        nextAction
                .setImageDescriptor(IDEInternalWorkbenchImages
                        .getImageDescriptor(IDEInternalWorkbenchImages.IMG_ETOOL_NEXT_NAV));
        register(nextAction);

        previousAction = ActionFactory.PREVIOUS.create(window);
        previousAction
                .setImageDescriptor(IDEInternalWorkbenchImages
                        .getImageDescriptor(IDEInternalWorkbenchImages.IMG_ETOOL_PREVIOUS_NAV));
        register(previousAction);

        buildProjectAction = IDEActionFactory.BUILD_PROJECT.create(window);
        register(buildProjectAction);

        openWorkspaceAction = IDEActionFactory.OPEN_WORKSPACE
                .create(window);
        register(openWorkspaceAction);

        projectPropertyDialogAction = IDEActionFactory.OPEN_PROJECT_PROPERTIES
                .create(window);
        register(projectPropertyDialogAction);

        if (window.getWorkbench().getIntroManager().hasIntro()) {
            introAction = ActionFactory.INTRO.create(window);
            register(introAction);
        }

        String showInQuickMenuId = IWorkbenchCommandConstants.NAVIGATE_SHOW_IN_QUICK_MENU;
        showInQuickMenu = new QuickMenuAction(showInQuickMenuId) {
            protected void fillMenu(IMenuManager menu) {
                menu.add(ContributionItemFactory.VIEWS_SHOW_IN
                        .create(window));
            }
        };
        register(showInQuickMenu);

        newQuickMenu = new QuickMenuAction(newQuickMenuId) {
            protected void fillMenu(IMenuManager menu) {
                menu.add(new NewWizardMenu(window));
            }
        };
        register(newQuickMenu);
        
        
