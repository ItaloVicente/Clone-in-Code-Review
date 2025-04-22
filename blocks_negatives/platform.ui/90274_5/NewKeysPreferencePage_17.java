		addBindingButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public final void widgetSelected(final SelectionEvent event) {
				keyController.getBindingModel().copy();
			}
		});
