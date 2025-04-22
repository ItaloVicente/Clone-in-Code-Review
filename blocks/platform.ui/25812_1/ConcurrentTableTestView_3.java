			});

			final Button limitSize = new Button(buttonBar, SWT.CHECK);
			limitSize.setLayoutData(new GridData(GridData.FILL_BOTH));
			limitSize.setText("Limit table size to 400");
			limitSize.addSelectionListener(new SelectionAdapter() {
				@Override
