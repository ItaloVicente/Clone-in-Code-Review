
	private void setNeedsSearch() {
		fTreeViewer.setInput(null);
		final File file = new File(dir.getText());
		if (!file.exists()) {
			setErrorMessage(NLS.bind(
					UIText.RepositorySearchDialog_DirectoryNotFoundMessage, dir
							.getText()));
		} else {
			setErrorMessage(null);
			setMessage(UIText.RepositorySearchDialog_NoSearchAvailableMessage,
					IMessageProvider.INFORMATION);
		}
		enableOk();
	}

	private void enableOk() {
		boolean enable = hasCheckedItems();
		getButton(OK).setEnabled(enable);
		if (enable)
			getButton(OK).setFocus();
	}
