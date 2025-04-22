    /**
     * Utility method that creates a new composite and
     * sets up its layout data.
     *
     * @param parent  the parent of the composite
     * @param numColumns  the number of columns in the new composite
     * @return the newly-created composite
     */
    protected Composite createComposite(Composite parent, int numColumns) {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = numColumns;
        composite.setLayout(layout);
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        composite.setLayoutData(data);
        return composite;
    }

    @Override
