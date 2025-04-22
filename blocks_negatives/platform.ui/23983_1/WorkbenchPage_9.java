    /**
     * See IWorkbenchPage@findView.
     */
    public IViewPart findView(String id) {
        IViewReference ref = findViewReference(id);
        if (ref == null) {
