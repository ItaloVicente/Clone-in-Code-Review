		final Button showCommandGroupFilterButton = new Button(hideMenuItemsComposite, SWT.CHECK);
		showCommandGroupFilterButton.setText(WorkbenchMessages.HideItems_turnOnActionSets);
		showCommandGroupFilterButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (showCommandGroupFilterButton.getSelection()) {
					Object o = ((StructuredSelection) menuStructureViewer1.getSelection()).getFirstElement();
					ActionSet initSelectAS = null;
					DisplayItem initSelectCI = null;
					if (o instanceof DisplayItem) {
						initSelectCI = ((DisplayItem) o);
						initSelectAS = initSelectCI.getActionSet();
					}
					if (initSelectAS == null) {
						initSelectAS = (ActionSet) actionSetViewer.getElementAt(0);
