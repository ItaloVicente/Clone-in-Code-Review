    public static final ActionFactory PROPERTIES = new ActionFactory(
            "properties", IWorkbenchCommandConstants.FILE_PROPERTIES) {//$NON-NLS-1$

        @Override
		public IWorkbenchAction create(IWorkbenchWindow window) {
            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(),WorkbenchMessages.Workbench_properties);
            action.setToolTipText(WorkbenchMessages.Workbench_propertiesToolTip);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };
