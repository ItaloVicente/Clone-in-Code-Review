    public static final ActionFactory PREVIOUS_PERSPECTIVE = new ActionFactory(
            "previousPerspective", IWorkbenchCommandConstants.WINDOW_PREVIOUS_PERSPECTIVE) {//$NON-NLS-1$

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CyclePerspectiveAction_prev_text);
            action.setToolTipText(WorkbenchMessages.CyclePerspectiveAction_prev_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
