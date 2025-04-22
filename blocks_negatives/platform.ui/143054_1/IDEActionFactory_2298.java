            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.Workbench_addBookmark);
            action.setToolTipText(IDEWorkbenchMessages.Workbench_addBookmarkToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "build", commandId: "org.eclipse.ui.project.buildAll"):
     * Incremental build. This action maintains its enablement state.
     */
    public static final ActionFactory BUILD = new ActionFactory("build",  //$NON-NLS-1$
    		IWorkbenchCommandConstants.PROJECT_BUILD_ALL) {

        @Override
