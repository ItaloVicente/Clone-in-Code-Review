
		searchBar = new SearchBar(
				CommitSelectionDialog.class.getName() + ".searchBar", table) {//$NON-NLS-1$

			@Override
			protected void showStatus(FindToolbar originator, String text) {
				statusLabel.setText(text);
				statusLabel.requestLayout();
			}

		};
		searchBar.fill(searchBarParent);

		Composite statusLine = new Composite(parent, SWT.NONE);
		statusLine.setLayout(new GridLayout());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(statusLine);
		statusLabel = new Label(statusLine, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER)
				.grab(false, false).applyTo(statusLabel);

