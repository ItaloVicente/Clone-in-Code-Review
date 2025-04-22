            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.Workbench_rebuildProject);
            action.setToolTipText(IDEWorkbenchMessages.Workbench_rebuildProjectToolTip);
            window.getPartService().addPartListener(action);
            action
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "tipsAndTricks", commandId: "org.eclipse.ui.help.tipsAndTricksAction"):
     * Tips and tricks. This action maintains its enablement state.
     */
    public static final ActionFactory TIPS_AND_TRICKS = new ActionFactory(
            "tipsAndTricks", IWorkbenchCommandConstants.HELP_TIPS_AND_TRICKS) { //$NON-NLS-1$

        @Override
