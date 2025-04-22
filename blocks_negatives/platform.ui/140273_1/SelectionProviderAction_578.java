    /**
     * Returns the current structured selection in the selection provider, or an
     * empty selection if nothing is selected or if selection does not include
     * objects (for example, raw text).
     *
     * @return the current structured selection in the selection provider
     */
    public IStructuredSelection getStructuredSelection() {
        ISelection selection = provider.getSelection();
        if (selection instanceof IStructuredSelection) {
