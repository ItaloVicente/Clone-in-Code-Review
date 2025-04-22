		changeBranch = new Button(checkoutGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().span(3, 1).applyTo(changeBranch);
		changeBranch.setText(UIText.FetchGerritChangePage_ChangeToLocalBranchRadio);
		changeBranch.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPage();
			}
		});

