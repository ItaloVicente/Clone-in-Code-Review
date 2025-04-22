    }

    /**
     * Create a new viewer in the parent.
     *
     * @param parent the parent <code>Composite</code>.
     */
    private void createViewer(Composite parent) {
        list = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.BORDER);
        list.getTable().setFont(parent.getFont());
