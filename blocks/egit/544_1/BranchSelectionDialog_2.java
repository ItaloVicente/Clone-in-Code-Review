		branchTree.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				refNameFromDialog();
				renameButton.setEnabled(null != refName && !Constants.HEAD.equals(refName));
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
