    public IWorkingSetPage createWorkingSetPage() {
        Object page = null;

        if (pageClassName != null) {
            try {
                page = WorkbenchPlugin.createExtension(configElement,
                        ATT_PAGE_CLASS);
            } catch (CoreException exception) {
                        pageClassName, exception.getStatus());
            }
        }
        return (IWorkingSetPage) page;
    }

    /**
     * Returns the page's icon
     *
     * @return the page's icon
     */
    public ImageDescriptor getIcon() {
        if (icon == null) {
