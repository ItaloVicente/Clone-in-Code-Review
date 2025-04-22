    public static final ActionFactory SHOW_PART_PANE_MENU = new ActionFactory(

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            WorkbenchCommandAction action=new WorkbenchCommandAction("org.eclipse.ui.window.showSystemMenu",window); //$NON-NLS-1$
            action.setId(getId());
            action.setText(WorkbenchMessages.ShowPartPaneMenuAction_text);
            action.setToolTipText(WorkbenchMessages.ShowPartPaneMenuAction_toolTip);
            return action;
        }
    };

	/**
	 * Workbench action (id: "showViewMenu", commandId: "org.eclipse.ui.window.showViewMenu"): Show
	 * the view menu. This action maintains its enablement state.
	 */
    public static final ActionFactory SHOW_VIEW_MENU = new ActionFactory(
            "showViewMenu", IWorkbenchCommandConstants.WINDOW_SHOW_VIEW_MENU) {//$NON-NLS-1$

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.ShowViewMenuAction_text);
            action.setToolTipText(WorkbenchMessages.ShowViewMenuAction_toolTip);
            return action;
        }
    };

	/**
	 * Workbench action (id: "undo", commandId: "org.eclipse.ui.edit.undo"): Undo. This action is a
	 * {@link RetargetAction} with id "undo". This action maintains its enablement state.
	 */
    public static final ActionFactory UNDO = new ActionFactory("undo", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_UNDO) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            LabelRetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_undo);
            action.setToolTipText(WorkbenchMessages.Workbench_undoToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_UNDO_DISABLED));
            return action;
        }
    };

	/**
	 * Workbench action (id: "up", commandId: "org.eclipse.ui.navigate.up"): Up. This action is a
	 * {@link RetargetAction} with id "up". This action maintains its enablement state.
	 */
    public static final ActionFactory UP = new ActionFactory("up", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_UP) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_up);
            action.setToolTipText(WorkbenchMessages.Workbench_upToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

	/**
	 * Workbench action (id: "helpContents", commandId: "org.eclipse.ui.help.helpContents"): Open
	 * the help contents. This action is always enabled.
	 */
    public static final ActionFactory HELP_CONTENTS = new ActionFactory(
            "helpContents", IWorkbenchCommandConstants.HELP_HELP_CONTENTS) {//$NON-NLS-1$

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new HelpContentsAction(window);
            action.setId(getId());
            return action;
        }
    };

	/**
	 * Workbench action (id: "helpSearch", commandId: "org.eclipse.ui.help.helpSearch"): Open the
	 * help search. This action is always enabled.
