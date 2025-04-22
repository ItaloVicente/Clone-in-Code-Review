		{
			final Button button = new Button(parent, SWT.CHECK);
			button.setText("dirty");
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					MySaveable saveable = (MySaveable) selection.getValue();
					saveable.setDirty(button.getSelection());
				}
			});
			new ControlUpdater(button) {
				@Override
				protected void updateControl() {
					MySaveable saveable = (MySaveable) selection.getValue();
					if (saveable == null) {
						button.setEnabled(false);
						button.setSelection(false);
					} else {
						button.setEnabled(true);
						button.setSelection(saveable.isDirty());
					}
