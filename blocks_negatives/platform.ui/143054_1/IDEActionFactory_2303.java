            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetActionWithDefault(getId(),
                    IDEWorkbenchMessages.Workbench_buildProject);
            action.setToolTipText(IDEWorkbenchMessages.Workbench_buildProjectToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "closeProject", commandId: "org.eclipse.ui.project.closeProject"):
     * Close project. This action is a {@link RetargetAction}. This action maintains its enablement state.
     */
