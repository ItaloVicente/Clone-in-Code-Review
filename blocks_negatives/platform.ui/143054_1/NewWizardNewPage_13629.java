    }

    /**
     * Create the Show All and help buttons at the bottom of the page.
     *
     * @param parent the parent composite on which to create the widgets
     */
    private void createOptionsButtons(Composite parent){
        if (needShowAll) {
            showAllCheck = new Button(parent, SWT.CHECK);
            GridData data = new GridData();
            showAllCheck.setLayoutData(data);
            showAllCheck.setFont(parent.getFont());
            showAllCheck.setText(WorkbenchMessages.NewWizardNewPage_showAll);
            showAllCheck.setSelection(false);

            showAllCheck.addSelectionListener(new SelectionAdapter() {

                private Object[] delta = new Object[0];

                @Override
