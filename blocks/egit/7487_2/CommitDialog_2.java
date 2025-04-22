		Section pushSection = toolkit.createSection(container,
				ExpandableComposite.TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(pushSection);
		Composite pushArea = toolkit.createComposite(pushSection);
		pushSection.setClient(pushArea);
		toolkit.paintBordersFor(pushArea);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(pushArea);
		pushSection.setText("Push"); //$NON-NLS-1$
		final Button pushCheckbox = toolkit.createButton(pushArea, "&Push the changes to upstream", SWT.CHECK); //$NON-NLS-1$
		canPush = SimpleConfigurePushDialog.getConfiguredRemote(repository) != null;
		pushCheckbox.setEnabled(canPush);
		pushCheckbox.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				pushing = pushCheckbox.getSelection();
			}
		});

