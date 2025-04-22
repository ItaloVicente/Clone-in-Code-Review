            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new GlobalBuildAction(window,
                    IncrementalProjectBuilder.FULL_BUILD);
            action.setId(getId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "rebuildProject"): Rebuild project.
     * This action is a {@link RetargetAction} with
     * id "rebuildProject". This action maintains its enablement state.
     *
     * @deprecated as of 3.0, this action no longer appears in the UI (was deprecated in 3.1)
     */
    @Deprecated
