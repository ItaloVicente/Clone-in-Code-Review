		branchTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refNameFromDialog();
				confirmationBtn.setEnabled(refName != null);
			}
		});
