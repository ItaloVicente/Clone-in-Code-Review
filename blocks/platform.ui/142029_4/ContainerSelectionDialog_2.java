		Composite area = (Composite) super.createDialogArea(parent);

		Listener listener = event -> {
			if (statusMessage != null && validator != null) {
				String errorMsg = validator.isValid(group.getContainerFullPath());
				if (errorMsg == null || errorMsg.equals(EMPTY_STRING)) {
					statusMessage.setText(EMPTY_STRING);
					getOkButton().setEnabled(true);
				} else {
					statusMessage.setText(errorMsg);
					getOkButton().setEnabled(false);
				}
			}
