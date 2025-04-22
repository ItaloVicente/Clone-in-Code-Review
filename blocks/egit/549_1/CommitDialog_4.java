		showUntrackedButton = new Button(container, SWT.CHECK);
		showUntrackedButton.setSelection(showUntracked);
		showUntrackedButton.setText(UIText.CommitDialog_ShowUntrackedFiles);
		showUntrackedButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());
		showUntrackedButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				showUntracked = showUntrackedButton.getSelection();
				filesViewer.refresh(true);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

