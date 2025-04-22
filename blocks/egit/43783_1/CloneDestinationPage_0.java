
		setRemoteWebAddr = new Button(g, SWT.CHECK);
		setRemoteWebAddr.setText("Remote Web Login Address"); //$NON-NLS-1$
		GridDataFactory.swtDefaults().span(2, 1).applyTo(setRemoteWebAddr);
		setRemoteWebAddr.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				remoteText.setText(uri);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
