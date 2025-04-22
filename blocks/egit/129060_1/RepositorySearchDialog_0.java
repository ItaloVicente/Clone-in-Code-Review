		Path file;
		try {
			file = Paths.get(dir.getText());
		} catch (InvalidPathException e) {
			setErrorMessage(MessageFormat.format(
					UIText.RepositorySearchDialog_DirectoryNotFoundMessage,
					dir.getText()));
			return;
		}
