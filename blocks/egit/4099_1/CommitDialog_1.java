	private void updateMessage() {
		String message = commitMessageComponent.getMessage();
		if (message == null && filesViewer.getCheckedElements().length == 0
				&& !amendingItem.getSelection())
			message = UIText.CommitDialog_MessageNoFilesSelected;
		setMessage(message, IMessageProvider.INFORMATION);
		commitButton.setEnabled(message == null);
	}

