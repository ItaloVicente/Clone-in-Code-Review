			this.branchCombo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					checkPage();
				}
			});
			if (myBaseBranch != null) {
				this.branchCombo.setText(myBaseBranch.getName());
