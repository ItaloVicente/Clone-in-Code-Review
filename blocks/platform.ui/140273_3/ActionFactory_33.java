			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_delete);
			action.setToolTipText(WorkbenchMessages.Workbench_deleteToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			action.enableAccelerator(false);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.DELETE_RETARGET_ACTION);
			ISharedImages sharedImages = window.getWorkbench().getSharedImages();
			action.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
			action.setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
			return action;
		}
	};

	public static final ActionFactory EDIT_ACTION_SETS = new ActionFactory("editActionSets", //$NON-NLS-1$
			IWorkbenchCommandConstants.WINDOW_CUSTOMIZE_PERSPECTIVE) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			WorkbenchCommandAction action = new WorkbenchCommandAction(getCommandId(), window);
			action.setId(getId());
			action.setText(WorkbenchMessages.EditActionSetsAction_text);
			action.setToolTipText(WorkbenchMessages.EditActionSetsAction_toolTip);
			window.getWorkbench().getHelpSystem().setHelp(action, IWorkbenchHelpContextIds.EDIT_ACTION_SETS_ACTION);

			return action;
		}
	};

	public static final ActionFactory EXPORT = new ActionFactory("export", //$NON-NLS-1$
			IWorkbenchCommandConstants.FILE_EXPORT) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
