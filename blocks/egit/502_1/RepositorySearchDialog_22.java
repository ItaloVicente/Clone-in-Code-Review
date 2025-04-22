		tab = tv.getTable();
		tab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		tab.setEnabled(false);

		btnToggleSelect = new Button(main, SWT.NONE);
		btnToggleSelect.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
				false, 1, 1));
		btnToggleSelect
				.setText(UIText.RepositorySearchDialog_ToggleSelection_button);
		btnToggleSelect.setEnabled(false);
		btnToggleSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				for (int i = 0; i < tab.getItemCount(); i++) {
					if (!existingRepositoryDirs.contains(tv.getElementAt(i)))
						tv.setChecked(tv.getElementAt(i), !tv.getChecked(tv
								.getElementAt(i)));
				}
				getButton(IDialogConstants.OK_ID).setEnabled(
						tv.getCheckedElements().length > 0);
			}
		});
