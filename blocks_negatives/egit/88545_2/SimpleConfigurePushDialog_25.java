				RefSpecDialog dlg = new RefSpecDialog(getShell(), repository,
						config, oldSpec, true);
				if (dlg.open() == Window.OK) {
					config.removePushRefSpec(oldSpec);
					config.addPushRefSpec(dlg.getSpec());
				}
				updateControls();
			}
		});
		final Button deleteRefSpec = new Button(refButtonArea, SWT.PUSH);
		deleteRefSpec
				.setText(UIText.SimpleConfigurePushDialog_DeleteRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(deleteRefSpec);
		deleteRefSpec.setEnabled(false);
		deleteRefSpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (Object spec : ((IStructuredSelection) specViewer
						.getSelection()).toArray())
					config.removePushRefSpec((RefSpec) spec);
