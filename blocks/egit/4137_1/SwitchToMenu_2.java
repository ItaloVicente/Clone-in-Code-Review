	private void createMenuItem(Menu menu, final Repository repository,
			String currentBranch, final String fullName, String shortName) {
		final MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText(shortName);
		boolean checkedOut = currentBranch.equals(fullName);
		if (checkedOut)
			item.setImage(checkedOutImage);
		else
			item.setImage(branchImage);
		item.setEnabled(!checkedOut);
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BranchOperationUI.checkout(repository, fullName)
						.start();
			}
		});
	}

