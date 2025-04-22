		final Button copySpec = new Button(refButtonArea, SWT.PUSH);
		copySpec.setText(UIText.SimpleConfigurePushDialog_CopyRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(copySpec);
		copySpec.setEnabled(false);
		copySpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String toCopy = ((IStructuredSelection) specViewer
						.getSelection()).getFirstElement().toString();
				Clipboard clipboard = new Clipboard(getShell().getDisplay());
				try {
					clipboard.setContents(new String[] { toCopy },
							new TextTransfer[] { TextTransfer.getInstance() });
				} finally {
					clipboard.dispose();
				}
			}
		});

		final Button pasteSpec = new Button(refButtonArea, SWT.PUSH);
		pasteSpec.setText(UIText.SimpleConfigurePushDialog_PasteRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(pasteSpec);
		pasteSpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doPaste();
			}
		});

		addRefSpecAdvanced = new Button(refButtonArea, SWT.PUSH);
		addRefSpecAdvanced
				.setText(UIText.SimpleConfigurePushDialog_EditAdvancedButton);
		GridDataFactory.fillDefaults().applyTo(addRefSpecAdvanced);
		addRefSpecAdvanced.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (new WizardDialog(getShell(), new RefSpecWizard(repository,
						config, true)).open() == Window.OK)
					updateControls();
			}
		});

		specViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) specViewer
						.getSelection();
				copySpec.setEnabled(sel.size() == 1);
				changeRefSpec.setEnabled(sel.size() == 1);
				deleteRefSpec.setEnabled(!sel.isEmpty());
			}
