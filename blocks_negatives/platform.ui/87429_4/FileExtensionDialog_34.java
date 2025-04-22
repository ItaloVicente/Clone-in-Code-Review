		filenameField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				if (event.widget == filenameField) {
					filename = filenameField.getText().trim();
					okButton.setEnabled(validateFileType());
				}
