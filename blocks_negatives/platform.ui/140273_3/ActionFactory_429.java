	 * Workbench action (id: "previousPerspective", commandId:
	 * "org.eclipse.ui.window.previousPerspective"): Previous perspective. This
	 * action maintains its enablement state.
	 * <p>
	 * <code>NEXT_PERSPECTIVE</code> and <code>PREVIOUS_PERSPECTIVE</code> form a
	 * cycle action pair. For a given window, use
	 * {@link ActionFactory#linkCycleActionPair ActionFactory.linkCycleActionPair}
	 * to connect the two.
	 * </p>
	 */
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
					IWorkbenchHelpContextIds.CYCLE_PERSPECTIVE_BACKWARD_ACTION);
            return action;
        }
    };

	/**
	 * Workbench action (id: "print", commandId: "org.eclipse.ui.file.print"): Print. This action is
	 * a {@link RetargetAction} with id "print". This action maintains its enablement state.
	 */
    public static final ActionFactory PRINT = new ActionFactory("print", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_PRINT) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_print);
            action.setToolTipText(WorkbenchMessages.Workbench_printToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            action
                    .setImageDescriptor(WorkbenchImages
                            .getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT));
            action
                    .setDisabledImageDescriptor(WorkbenchImages
                            .getImageDescriptor(ISharedImages.IMG_ETOOL_PRINT_EDIT_DISABLED));
            return action;
        }
    };

	/**
	 * Workbench action (id: "properties", commandId: "org.eclipse.ui.file.properties"): Properties.
	 * This action is a {@link RetargetAction} with id "properties". This action maintains its
