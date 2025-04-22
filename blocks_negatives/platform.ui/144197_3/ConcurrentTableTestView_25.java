		{

			updateCount = new Label(buttonBar, SWT.NONE);
			updateCount.setLayoutData(new GridData(GridData.FILL_BOTH));

			slowComparisons = new Button(buttonBar, SWT.CHECK);
			slowComparisons.setLayoutData(new GridData(GridData.FILL_BOTH));
			slowComparisons.setText("Slow comparisons");
			slowComparisons.setSelection(enableSlowComparisons);
			slowComparisons.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					enableSlowComparisons = slowComparisons.getSelection();
					super.widgetSelected(e);
				}
			});

			final Button limitSize = new Button(buttonBar, SWT.CHECK);
			limitSize.setLayoutData(new GridData(GridData.FILL_BOTH));
			limitSize.setText("Limit table size to 400");
			limitSize.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (limitSize.getSelection()) {
						contentProvider.setLimit(400);
					} else {
						contentProvider.setLimit(-1);
					}
					super.widgetSelected(e);
				}
			});

			Button resetCountButton = new Button(buttonBar, SWT.PUSH);
			resetCountButton.setLayoutData(new GridData(GridData.FILL_BOTH));
			resetCountButton.setText("Reset comparison count");
			resetCountButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					comparator.comparisons = 0;
					scheduleComparisonUpdate();
				}
			});

			Button testButton = new Button(buttonBar, SWT.PUSH);
			testButton.setLayoutData(new GridData(GridData.FILL_BOTH));
			testButton.setText("add 100000 elements");
			testButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					addRandomElements(100000);
				}
			});

			Button removeButton = new Button(buttonBar, SWT.PUSH);
			removeButton.setLayoutData(new GridData(GridData.FILL_BOTH));
			removeButton.setText("remove all");
			removeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					clear();
