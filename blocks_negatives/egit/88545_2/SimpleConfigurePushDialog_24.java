		specViewer = new TableViewer(refSpecGroup, SWT.BORDER | SWT.MULTI);
		specViewer.setContentProvider(ArrayContentProvider.getInstance());
		GridDataFactory.fillDefaults().grab(true, true)
				.minSize(SWT.DEFAULT, 30).applyTo(specViewer.getTable());
		specViewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.MOD1 && e.keyCode == 'v')
					doPaste();
			}
		});

		final Composite refButtonArea = new Composite(refSpecGroup, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(refButtonArea);
		GridDataFactory.fillDefaults().grab(false, true).minSize(SWT.DEFAULT, SWT.DEFAULT).applyTo(refButtonArea);

		addRefSpec = new Button(refButtonArea, SWT.PUSH);
		addRefSpec.setText(UIText.SimpleConfigurePushDialog_AddRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(addRefSpec);
		addRefSpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RefSpecDialog dlg = new RefSpecDialog(getShell(), repository,
						config, true);
				if (dlg.open() == Window.OK)
					config.addPushRefSpec(dlg.getSpec());
				updateControls();
			}
		});

		changeRefSpec = new Button(refButtonArea, SWT.PUSH);
		changeRefSpec
				.setText(UIText.SimpleConfigurePushDialog_ChangeRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(changeRefSpec);
		changeRefSpec.setEnabled(false);
		GridDataFactory.fillDefaults().exclude(!advancedMode)
				.applyTo(changeRefSpec);
		changeRefSpec.addSelectionListener(new SelectionAdapter() {
