		} catch (IOException e1) {
		}

		this.branchCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPage();
