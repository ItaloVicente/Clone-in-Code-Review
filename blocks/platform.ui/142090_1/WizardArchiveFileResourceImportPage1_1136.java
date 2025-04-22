		}

		sourceNameField.setFocus();
		return null;
	}

	protected TarFile getSpecifiedTarSourceFile() {
		return getSpecifiedTarSourceFile(sourceNameField.getText());
	}

	private TarFile getSpecifiedTarSourceFile(String fileName) {
		if (fileName.length() == 0) {
