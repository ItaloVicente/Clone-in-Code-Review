    public static final ActionFactory PREVIOUS_EDITOR = new ActionFactory(
            "previousEditor", IWorkbenchCommandConstants.WINDOW_PREVIOUS_EDITOR) {//$NON-NLS-1$

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			IWorkbenchAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CycleEditorAction_prev_text);
            action.setToolTipText(WorkbenchMessages.CycleEditorAction_prev_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_EDITOR_BACKWARD_ACTION);
