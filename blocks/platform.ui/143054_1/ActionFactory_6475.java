	public static final ActionFactory PROPERTIES = new ActionFactory("properties", //$NON-NLS-1$
			IWorkbenchCommandConstants.FILE_PROPERTIES) {

		@Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
			if (window == null) {
				throw new IllegalArgumentException();
			}
			RetargetAction action = new RetargetAction(getId(), WorkbenchMessages.Workbench_properties);
			action.setToolTipText(WorkbenchMessages.Workbench_propertiesToolTip);
			window.getPartService().addPartListener(action);
			action.setActionDefinitionId(getCommandId());
			return action;
		}
	};
