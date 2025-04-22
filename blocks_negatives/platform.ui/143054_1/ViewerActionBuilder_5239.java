    /**
     * Basic contstructor
     */
    public ViewerActionBuilder() {
    }

    @Override
	protected ActionDescriptor createActionDescriptor(
            IConfigurationElement element) {
        if (part instanceof IViewPart) {
