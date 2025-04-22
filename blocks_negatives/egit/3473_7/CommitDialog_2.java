		showUntrackedButton = new Button(container, SWT.CHECK);
		showUntrackedButton.setText(UIText.CommitDialog_ShowUntrackedFiles);
		showUntrackedButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());

		IDialogSettings settings = org.eclipse.egit.ui.Activator.getDefault()
				.getDialogSettings();
		if (settings.get(SHOW_UNTRACKED_PREF) != null) {
			showUntracked = Boolean.valueOf(settings.get(SHOW_UNTRACKED_PREF))
					.booleanValue();
		}

		showUntrackedButton.setSelection(showUntracked);

		showUntrackedButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				showUntracked = showUntrackedButton.getSelection();
				filesViewer.refresh(true);
			}

		});

