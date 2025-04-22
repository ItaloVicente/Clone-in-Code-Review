			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new OpenPreferencesAction(window);
			action.setId(getId());
			return action;
		}
	};

	public static final ActionFactory PREVIOUS = new ActionFactory("previous", //$NON-NLS-1$
			IWorkbenchCommandConstants.NAVIGATE_PREVIOUS) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new LabelRetargetAction(getId(), WorkbenchMessages.Workbench_previous);
			action.setToolTipText(WorkbenchMessages.Workbench_previousToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};

	public static final ActionFactory PREVIOUS_EDITOR = new ActionFactory("previousEditor", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_PREVIOUS_EDITOR) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			IWorkbenchAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.CycleEditorAction_prev_text);
			action.setToolTipText(WorkbenchMessages.CycleEditorAction_prev_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_EDITOR_BACKWARD_ACTION);

			return action;
		}
	};

	public static final ActionFactory PREVIOUS_PART = new ActionFactory("previousPart", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_PREVIOUS_VIEW) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
