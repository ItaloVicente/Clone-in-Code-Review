		normalizeName = new Button(main, SWT.CHECK);
		normalizeName.setText(UIText.CreateBranchPage_NormalizeBranchName);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				.applyTo(normalizeName);
		normalizeName.setSelection(true);
		normalizeName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				branchNormalizer.modifyText(null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

