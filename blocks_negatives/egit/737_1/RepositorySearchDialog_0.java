		Button search = new Button(main, SWT.PUSH);
		search.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		search.setText(UIText.RepositorySearchDialog_search);

		tv = CheckboxTableViewer.newCheckList(main, SWT.BORDER);
		tab = tv.getTable();
		tab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		tab.setEnabled(false);
