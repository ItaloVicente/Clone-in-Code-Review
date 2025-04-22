    /**
     * @see IWorkbenchPage
     */
    public IAdaptable getInput() {
        return input;
    }

    /**
     * Returns the page label. This is a combination of the page input and
     * active perspective.
     */
    public String getLabel() {
        String label = WorkbenchMessages.WorkbenchPage_UnknownLabel;
        IWorkbenchAdapter adapter = (IWorkbenchAdapter) Util.getAdapter(input, 
                IWorkbenchAdapter.class);
        if (adapter != null) {
