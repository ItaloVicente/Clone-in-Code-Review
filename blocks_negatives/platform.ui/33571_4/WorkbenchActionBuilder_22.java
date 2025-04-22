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
