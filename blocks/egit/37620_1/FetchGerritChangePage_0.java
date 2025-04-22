		cherryPick = new Button(checkoutGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().span(3, 1).applyTo(cherryPick);
		cherryPick.setText(UIText.FetchGerritChangePage_CherryPickRadio);
		cherryPick.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPage();
			}
		});

