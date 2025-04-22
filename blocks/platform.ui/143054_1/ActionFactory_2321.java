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

	public static final ActionFactory HELP_CONTENTS = new ActionFactory("helpContents", //$NON-NLS-1$
			IWorkbenchCommandConstants.HELP_HELP_CONTENTS) {

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

