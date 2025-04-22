		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_move);
			action.setToolTipText(WorkbenchMessages.Workbench_moveToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory NEW = new ActionFactory("new", //$NON-NLS-1$
			IWorkbenchCommandConstants.FILE_NEW) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			ISharedImages images = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
			action.setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
			action.setText(WorkbenchMessages.NewWizardAction_text);
			action.setToolTipText(WorkbenchMessages.NewWizardAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.NEW_ACTION);
			return action;
		}
	};

	public static final ActionFactory NEW_WIZARD_DROP_DOWN = new ActionFactory("newWizardDropDown") { //$NON-NLS-1$

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new NewWizardDropDownAction(window);
			action.setId(getId());
			return action;
		}
	};

	public static final ActionFactory NEXT = new ActionFactory("next", //$NON-NLS-1$
			IWorkbenchCommandConstants.NAVIGATE_NEXT) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_next);
			action.setToolTipText(WorkbenchMessages.Workbench_nextToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory NEXT_EDITOR = new ActionFactory("nextEditor", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_NEXT_EDITOR) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new WorkbenchCommandAction(getCommandId(), window);

			action.setId(getId());
			action.setText(WorkbenchMessages.CycleEditorAction_next_text);
			action.setToolTipText(WorkbenchMessages.CycleEditorAction_next_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.CYCLE_EDITOR_FORWARD_ACTION);

			return action;
		}
	};

	public static final ActionFactory NEXT_PART = new ActionFactory("nextPart", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_NEXT_VIEW) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.CyclePartAction_next_text);
			action.setToolTipText(WorkbenchMessages.CyclePartAction_next_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.CYCLE_PART_FORWARD_ACTION);
			return action;
		}
	};

	public static final ActionFactory NEXT_PERSPECTIVE = new ActionFactory("nextPerspective", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_NEXT_PERSPECTIVE) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.CyclePerspectiveAction_next_text);
			action.setToolTipText(WorkbenchMessages.CyclePerspectiveAction_next_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PERSPECTIVE_FORWARD_ACTION);
			return action;
		}
	};

	public static final ActionFactory OPEN_NEW_WINDOW = new ActionFactory("openNewWindow", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_NEW_WINDOW) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.OpenInNewWindowAction_text);
			action.setToolTipText(WorkbenchMessages.OpenInNewWindowAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.OPEN_NEW_WINDOW_ACTION);
			return action;
		}

	};

	public static final ActionFactory PASTE = new ActionFactory("paste", //$NON-NLS-1$
			IWorkbenchCommandConstants.EDIT_PASTE) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_paste);
			action.setToolTipText(WorkbenchMessages.Workbench_pasteToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			ISharedImages sharedImages = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
			action.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
			return action;
		}
	};

