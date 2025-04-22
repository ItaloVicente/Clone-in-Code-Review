		valueText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (valueText.getText().length() == 0) {
					setErrorMessage(UIText.ConfigurationEditorComponent_EmptyStringNotAllowed);
					changeValue.setEnabled(false);
				} else {
					setErrorMessage(null);
					changeValue.setEnabled(true);
				}
			}
		});

