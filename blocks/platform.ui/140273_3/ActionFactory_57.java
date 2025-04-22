	public static final ActionFactory SHOW_PART_PANE_MENU = new ActionFactory("showPartPaneMenu") {//$NON-NLS-1$

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction("org.eclipse.ui.window.showSystemMenu", window); //$NON-NLS-1$
			action.setId(getId());
			action.setText(WorkbenchMessages.ShowPartPaneMenuAction_text);
			action.setToolTipText(WorkbenchMessages.ShowPartPaneMenuAction_toolTip);
			return action;
		}
	};

	public static final ActionFactory SHOW_VIEW_MENU = new ActionFactory("showViewMenu", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_SHOW_VIEW_MENU) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.ShowViewMenuAction_text);
			action.setToolTipText(WorkbenchMessages.ShowViewMenuAction_toolTip);
			return action;
		}
	};

	public static final ActionFactory UNDO = new ActionFactory("undo", //$NON-NLS-1$
			IWorkbenchCommandConstants.EDIT_UNDO) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			LabelRetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_undo);
			action.setToolTipText(WorkbenchMessages.Workbench_undoToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			ISharedImages sharedImages = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO));
			action.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_UNDO_DISABLED));
			return action;
		}
	};

	public static final ActionFactory UP = new ActionFactory("up", //$NON-NLS-1$
			IWorkbenchCommandConstants.NAVIGATE_UP) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_up);
			action.setToolTipText(WorkbenchMessages.Workbench_upToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

