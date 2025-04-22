		if (name.equals(IWorkbenchRegistryConstants.TAG_ACTION_SET)) {
			return ActionDescriptor.T_WORKBENCH;
		} else if (name.equals(IWorkbenchRegistryConstants.TAG_VIEW_CONTRIBUTION)) {
			return ActionDescriptor.T_VIEW;
		} else if (name.equals(IWorkbenchRegistryConstants.TAG_EDITOR_CONTRIBUTION)) {
			return ActionDescriptor.T_EDITOR;
		}
