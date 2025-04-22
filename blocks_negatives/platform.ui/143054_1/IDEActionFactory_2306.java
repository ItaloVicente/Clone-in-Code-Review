            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction innerAction = ActionFactory.NEW.create(window);
            NewWizardMenu newWizardMenu = new NewWizardMenu(window);
            IWorkbenchAction action = new NewWizardDropDownAction(window,
                    innerAction, newWizardMenu);
            action.setId(getId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "openProject", commandId: "org.eclipse.ui.project.openProject"):
     * Open project. This action is a {@link RetargetAction}. This action maintains its enablement state.
     */
    public static final ActionFactory OPEN_PROJECT = new ActionFactory(
            "openProject", IWorkbenchCommandConstants.PROJECT_OPEN_PROJECT) { //$NON-NLS-1$

        @Override
