            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new ToggleAutoBuildAction(window);
            action.setId(getId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "buildProject", commandId: "org.eclipse.ui.project.buildProject"):
     * Incremental build. This action is a {@link RetargetAction}. This action maintains its enablement state.
     */
    public static final ActionFactory BUILD_PROJECT = new ActionFactory(
            "buildProject", IWorkbenchCommandConstants.PROJECT_BUILD_PROJECT) { //$NON-NLS-1$

        @Override
