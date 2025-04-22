            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new org.eclipse.ui.actions.QuickStartAction(window);
            action.setId(getId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "rebuildAll"): Full build.
     * This action maintains its enablement state.
     *
     * @deprecated as of 3.0, this action no longer appears in the UI (was deprecated in 3.1)
     */
    @Deprecated
