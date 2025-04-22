		final IInputValidator inputValidator = ValidationUtils
				.getRefNameInputValidator(repository, prefix, true);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String error = inputValidator.isValid(name.getText());
				setErrorMessage(error);
				getButton(OK).setEnabled(error == null);
			}
		});

