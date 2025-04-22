    @Override
	protected ActionDescriptor createActionDescriptor(
            org.eclipse.core.runtime.IConfigurationElement element) {
        return new ActionDescriptor(element, ActionDescriptor.T_VIEW,
                targetPart);
    }
