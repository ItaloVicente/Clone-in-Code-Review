	    switch (name) {
	    	case IWorkbenchRegistryConstants.TAG_ACTION_SET:
		    return ActionDescriptor.T_WORKBENCH;
	    	case IWorkbenchRegistryConstants.TAG_VIEW_CONTRIBUTION:
		    return ActionDescriptor.T_VIEW;
	    	case IWorkbenchRegistryConstants.TAG_EDITOR_CONTRIBUTION:
		    return ActionDescriptor.T_EDITOR;
	    	default:
		    break;
	    }
