        actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(),
                refreshAction);
        actionBars.setGlobalActionHandler(IDEActionFactory.BUILD_PROJECT
                .getId(), buildAction);
        actionBars.setGlobalActionHandler(
                IDEActionFactory.OPEN_PROJECT.getId(), openProjectAction);
        actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT
                .getId(), closeProjectAction);
        actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_UNRELATED_PROJECTS
                .getId(), closeUnrelatedProjectsAction);
    }

    /**
     * Adds the build, open project, close project and refresh resource
     * actions to the context menu.
     * <p>
     * The following conditions apply:
     * 	build-only projects selected, auto build disabled, at least one
     * 		builder present
     * 	open project-only projects selected, at least one closed project
     * 	close project-only projects selected, at least one open project
     * 	refresh-no closed project selected
     * </p>
     * <p>
     * Both the open project and close project action may be on the menu
     * at the same time.
     * </p>
     * <p>
     * No disabled action should be on the context menu.
     * </p>
     *
     * @param menu context menu to add actions to
     */
    @Override
