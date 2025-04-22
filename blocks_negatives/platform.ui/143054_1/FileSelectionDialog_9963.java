    }

    /**
     * Add the selection and deselection buttons to the dialog.
     * @param composite org.eclipse.swt.widgets.Composite
     */
    private void addSelectionButtons(Composite composite) {

        Composite buttonComposite = new Composite(composite, SWT.RIGHT);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        buttonComposite.setLayout(layout);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END);
        composite.setData(data);

        Button selectButton = new Button(buttonComposite, SWT.PUSH);
        selectButton.setText(SELECT_ALL_TITLE);
        SelectionListener listener = new SelectionAdapter() {
            @Override
