            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.Workbench_addTask);
            action.setToolTipText(IDEWorkbenchMessages.Workbench_addTaskToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "bookmark", commandId: "org.eclipse.ui.edit.addBookmark"): Add bookmark.
     * This action is a {@link RetargetAction}. This action maintains its enablement state.
     */
    public static final ActionFactory BOOKMARK = new ActionFactory("bookmark", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_ADD_BOOKMARK) {

        @Override
