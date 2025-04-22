    /**
     * Add the selection and deselection buttons to the dialog.
     *
     * @param composite
     *            org.eclipse.swt.widgets.Composite
     */
    private void addSelectionButtons(Composite composite) {
        Composite buttonComposite = new Composite(composite, SWT.RIGHT);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        buttonComposite.setLayout(layout);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END
                | GridData.GRAB_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        composite.setData(data);
        Button selectButton = createButton(buttonComposite,
                IDialogConstants.SELECT_ALL_ID, WorkbenchMessages.WizardTransferPage_selectAll, false);
        SelectionListener listener = widgetSelectedAdapter(e -> listViewer.setAllChecked(true));
        selectButton.addSelectionListener(listener);
        Button deselectButton = createButton(buttonComposite,
                IDialogConstants.DESELECT_ALL_ID, WorkbenchMessages.WizardTransferPage_deselectAll, false);
        listener = widgetSelectedAdapter(e -> listViewer.setAllChecked(false));
        deselectButton.addSelectionListener(listener);
    }
