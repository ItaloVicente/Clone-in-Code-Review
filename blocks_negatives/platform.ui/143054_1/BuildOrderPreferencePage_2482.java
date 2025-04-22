        Object[] result = dialog.getResult();

        int currentItemsLength = currentItems.length;
        int resultLength = result.length;
        String[] newItems = new String[currentItemsLength + resultLength];

        System.arraycopy(currentItems, 0, newItems, 0, currentItemsLength);
        System
                .arraycopy(result, 0, newItems, currentItemsLength,
                        result.length);
        this.buildList.setItems(newItems);
    }

    /**
     * Updates the valid state of the page.
     */
    private void updateValidState() {
        setValid(maxItersField.isValid());
    }

    /**
     * Create the list of build paths. If the current build order is empty make the list empty
     * and disable it.
     * @param composite - the parent to create the list in
     * @param enabled - the boolean that indcates if the list will be sensitive initially or not
     */
    private void createBuildOrderList(Composite composite, boolean enabled) {

        Font font = composite.getFont();

        this.buildLabel = new Label(composite, SWT.NONE);
        this.buildLabel.setText(LIST_LABEL);
        this.buildLabel.setEnabled(enabled);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        this.buildLabel.setLayoutData(gridData);
        this.buildLabel.setFont(font);

        this.buildList = new List(composite, SWT.BORDER | SWT.MULTI
                | SWT.H_SCROLL | SWT.V_SCROLL);
        this.buildList.setEnabled(enabled);
        GridData data = new GridData();
        data.heightHint = buildList.getItemHeight();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        this.buildList.setLayoutData(data);
        this.buildList.setFont(font);
    }

    /**
     * Create the widgets that are used to determine the build order.
     *
     * @param parent the parent composite
     * @return the new control
     */
    @Override
