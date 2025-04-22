		configureFetch = new Button(main, SWT.CHECK);
		configureFetch.setText("Configure Fetch");
		configureFetch.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPage();
			}

		});

		GridDataFactory.fillDefaults().span(2, 1).applyTo(configureFetch);

		configurePush = new Button(main, SWT.CHECK);
		configurePush.setText("Configure Push");
		configurePush.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPage();
			}

		});
		GridDataFactory.fillDefaults().span(2, 1).applyTo(configurePush);

