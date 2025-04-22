		locationPathField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				errorReporter.reportError(checkValidLocation(), false);
			}
		});
