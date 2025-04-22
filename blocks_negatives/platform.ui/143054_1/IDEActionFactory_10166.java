            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.CloseUnrelatedProjectsAction_text);
            action.setToolTipText(IDEWorkbenchMessages.CloseUnrelatedProjectsAction_toolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "newWizardDropDown"): Opens the "new" wizard drop down, including
     * resource-specific items for Project... and Example...
     * This action maintains its enablement state.
     */
    public static final ActionFactory NEW_WIZARD_DROP_DOWN = new ActionFactory(

        @Override
