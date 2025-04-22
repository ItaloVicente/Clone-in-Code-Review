		try {
			Path file = Paths.get(dir.getText());
			if (!Files.isDirectory(file)) {
				setErrorMessage(MessageFormat.format(
						UIText.RepositorySearchDialog_DirectoryNotFoundMessage,
						dir.getText()));
				searchButton.setEnabled(false);
			} else {
				searchButton.setEnabled(true);
				setErrorMessage(null);
				setMessage(
						UIText.RepositorySearchDialog_NoSearchAvailableMessage,
						IMessageProvider.INFORMATION);
			}
		} catch (InvalidPathException e) {
