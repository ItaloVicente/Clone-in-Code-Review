		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.CyclePartAction_prev_text);
			action.setToolTipText(WorkbenchMessages.CyclePartAction_prev_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.CYCLE_PART_BACKWARD_ACTION);
			return action;
		}
	};

	public static final ActionFactory PREVIOUS_PERSPECTIVE = new ActionFactory("previousPerspective", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_PREVIOUS_PERSPECTIVE) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.CyclePerspectiveAction_prev_text);
			action.setToolTipText(WorkbenchMessages.CyclePerspectiveAction_prev_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PERSPECTIVE_BACKWARD_ACTION);
			return action;
		}
	};

	public static final ActionFactory PRINT = new ActionFactory("print", //$NON-NLS-1$
			IWorkbenchCommandConstants.FILE_PRINT) {

		@Override
