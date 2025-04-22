            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.CyclePartAction_next_text);
			action.setToolTipText(WorkbenchMessages.CyclePartAction_next_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action,
					IWorkbenchHelpContextIds.CYCLE_PART_FORWARD_ACTION);
            return action;
        }
    };

	/**
	 * Workbench action (id: "nextPerspective", commandId: "org.eclipse.ui.window.nextPerspective"):
	 * Next perspective. This action maintains its enablement state.
	 * <p>
	 * <code>NEXT_PERSPECTIVE</code> and <code>PREVIOUS_PERSPECTIVE</code> form a cycle action pair.
	 * For a given window, use {@link ActionFactory#linkCycleActionPair
	 * ActionFactory.linkCycleActionPair</code>} to connect the two.
	 * </p>
	 */
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
					IWorkbenchHelpContextIds.CYCLE_PERSPECTIVE_FORWARD_ACTION);
            return action;
        }
    };
