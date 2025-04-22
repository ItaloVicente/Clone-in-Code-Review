    /**
     * Update the button enablement state.
     */
    protected void updateButtons() {
        if (okButton != null) {
            okButton.setEnabled(getSelection().length > 0);
        }
    }

    /**
     * Update the selection object.
     */
    protected void updateSelection(SelectionChangedEvent event) {
        ArrayList descs = new ArrayList();
        IStructuredSelection sel = (IStructuredSelection) event.getSelection();
        for (Iterator i = sel.iterator(); i.hasNext();) {
            Object o = i.next();
            if (o instanceof IViewDescriptor) {
                descs.add(o);
