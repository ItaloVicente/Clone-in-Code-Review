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
