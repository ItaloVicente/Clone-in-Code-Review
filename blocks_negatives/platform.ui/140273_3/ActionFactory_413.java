        }
    };

	/**
	 * Workbench action (id: "copy", commandId: "org.eclipse.ui.edit.copy"): Copy. This action is a
	 * {@link RetargetAction} with id "copy". This action maintains its enablement state.
	 */
    public static final ActionFactory COPY = new ActionFactory("copy", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_COPY) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_copy);
            action.setToolTipText(WorkbenchMessages.Workbench_copyToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
            return action;
        }
    };

	/**
	 * Workbench action (id: "cut", commandId: "org.eclipse.ui.edit.cut"): Cut. This action is a
	 * {@link RetargetAction} with id "cut". This action maintains its enablement state.
	 */
    public static final ActionFactory CUT = new ActionFactory("cut", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_CUT) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_cut);
            action.setToolTipText(WorkbenchMessages.Workbench_cutToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
            action.setDisabledImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
            return action;
        }
    };

	/**
	 * Workbench action (id: "delete", commandId: "org.eclipse.ui.edit.delete"): Delete. This action
	 * is a {@link RetargetAction} with id "delete". This action maintains its enablement state.
	 */
    public static final ActionFactory DELETE = new ActionFactory("delete", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_DELETE) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_delete);
            action.setToolTipText(WorkbenchMessages.Workbench_deleteToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            action.enableAccelerator(false);
            window.getWorkbench().getHelpSystem().setHelp(action,
                    IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
            ISharedImages sharedImages = window.getWorkbench()
                    .getSharedImages();
            action.setImageDescriptor(sharedImages
                    .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
            action
                    .setDisabledImageDescriptor(sharedImages
                            .getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
            return action;
        }
    };

	/**
	 * Workbench action (id: "editActionSets", commandId: "org.eclipse.ui.window.customizePerspective"):
	 * Edit the action sets. This action maintains its enablement state.
	 */
    public static final ActionFactory EDIT_ACTION_SETS = new ActionFactory(
            "editActionSets", IWorkbenchCommandConstants.WINDOW_CUSTOMIZE_PERSPECTIVE) {//$NON-NLS-1$

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.EditActionSetsAction_text);
            action.setToolTipText(WorkbenchMessages.EditActionSetsAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.EDIT_ACTION_SETS_ACTION);

            return action;
        }
    };

	/**
	 * Workbench action (id: "export", commandId: "org.eclipse.ui.file.export"): Opens the export
	 * wizard. This action maintains its enablement state.
	 */
    public static final ActionFactory EXPORT = new ActionFactory("export", //$NON-NLS-1$
    		IWorkbenchCommandConstants.FILE_EXPORT) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }

			WorkbenchCommandAction action = new WorkbenchCommandAction(
					getCommandId(), window);
            action.setId(getId());
            action.setText(WorkbenchMessages.ExportResourcesAction_fileMenuText);
            action.setToolTipText(WorkbenchMessages.ExportResourcesAction_toolTip);
            window.getWorkbench().getHelpSystem().setHelp(action,
    				IWorkbenchHelpContextIds.EXPORT_ACTION);
            action.setImageDescriptor(WorkbenchImages
                    .getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_EXPORT_WIZ));
            return action;
        }

    };

	/**
	 * Workbench action (id: "find", commandId: "org.eclipse.ui.edit.findReplace"): Find. This
	 * action is a {@link RetargetAction} with id "find". This action maintains its enablement
	 * state.
	 */
    public static final ActionFactory FIND = new ActionFactory("find", //$NON-NLS-1$
    		IWorkbenchCommandConstants.EDIT_FIND_AND_REPLACE) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_findReplace);
            action.setToolTipText(WorkbenchMessages.Workbench_findReplaceToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

	/**
	 * Workbench action (id: "forward", commandId: "org.eclipse.ui.navigate.forward"): Forward. This
	 * action is a {@link RetargetAction} with id "forward". This action maintains its enablement
	 * state.
	 */
    public static final ActionFactory FORWARD = new ActionFactory("forward", //$NON-NLS-1$
    		IWorkbenchCommandConstants.NAVIGATE_FORWARD) {

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new LabelRetargetAction(getId(),WorkbenchMessages.Workbench_forward);
            action.setToolTipText(WorkbenchMessages.Workbench_forwardToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };
