            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new OpenWorkspaceAction(window);
            action.setId(getId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "projectProperties"): Open project properties.
     * This action maintains its enablement state.
     */
    public static final ActionFactory OPEN_PROJECT_PROPERTIES = new ActionFactory(

        @Override
