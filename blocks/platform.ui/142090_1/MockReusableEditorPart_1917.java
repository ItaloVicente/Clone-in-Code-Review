				setSaveNeeded(saveNeededToggle.getSelection());
			}
		});
		saveNeededToggle.setSelection(saveNeeded);

		final Button saveAsToggle = new Button(parent, SWT.CHECK);
		saveAsToggle.setText("Save as allowed");
		saveAsToggle.addSelectionListener(new SelectionAdapter() {
			@Override
