            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new BuildCleanAction(window);
            action.setId(getId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "buildAutomatically"): Build automatically.
     * This action maintains its enablement state.
     * @since 3.0
     */

        @Override
