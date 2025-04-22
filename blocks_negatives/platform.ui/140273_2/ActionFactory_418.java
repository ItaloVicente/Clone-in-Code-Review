    public static final ActionFactory NEXT_PERSPECTIVE = new ActionFactory(
            "nextPerspective", IWorkbenchCommandConstants.WINDOW_NEXT_PERSPECTIVE) {//$NON-NLS-1$

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CyclePerspectiveAction_next_text);
            action.setToolTipText(WorkbenchMessages.CyclePerspectiveAction_next_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
