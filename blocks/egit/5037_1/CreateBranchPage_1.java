		this.branchCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String ref = branchCombo.getText();
				suggestBranchName(ref);
				upstreamConfig = getDefaultUpstreamConfig(myRepository, ref);
				checkPage();
			}
		});

