	}

	private void createOptionsButtons(Composite parent) {
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
