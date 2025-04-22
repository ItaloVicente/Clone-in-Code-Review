            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.OpenResourceAction_text);
            action.setToolTipText(IDEWorkbenchMessages.OpenResourceAction_toolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "openWorkspace"): Open workspace.
     * This action maintains its enablement state.
     */
    public static final ActionFactory OPEN_WORKSPACE = new ActionFactory(

        @Override
