            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new ProjectPropertyDialogAction(window);
            action.setId(getId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "quickStart"): Quick start.
     * This action maintains its enablement state.
     *
     * @deprecated the IDE now uses the new intro mechanism
     */
    @Deprecated
