		if (calculatedCommitMessage != null)
			return calculatedCommitMessage;
		else
	}

	private ICommitMessageProvider getCommitMessageProvider()
			throws CoreException {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(COMMIT_MESSAGE_PROVIDER_ID);
		if (config.length > 0) {
			Object provider;
			if (provider instanceof ICommitMessageProvider) {
				return (ICommitMessageProvider) provider;
			} else {
				Activator.logError(UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
						null);
			}
		}
		return null;
	}

	private void saveOriginalChangeId() {
		int changeIdOffset = findOffsetOfChangeIdLine(previousCommitMessage);
		if (changeIdOffset > 0) {
			int endOfChangeId = findNextEOL(changeIdOffset, previousCommitMessage);
			if (endOfChangeId < 0)
				endOfChangeId = previousCommitMessage.length()-1;
			try {
				originalChangeId = ObjectId.fromString(previousCommitMessage.substring(sha1Offset, endOfChangeId));
			} catch (IllegalArgumentException e) {
				originalChangeId = null;
			}
		} else
			originalChangeId = null;
	}

	private int findNextEOL(int oldPos, String message) {
		return message.indexOf("\n", oldPos + 1); //$NON-NLS-1$
	}

	private int findOffsetOfChangeIdLine(String message) {
	}

	private void updateChangeIdButton() {
		String curText = commitText.getText();
		if (!curText.endsWith(Text.DELIMITER))
			curText += Text.DELIMITER;

		if (hasId) {
			changeIdItem.setSelection(true);
			createChangeId = true;
		}
	}

	private void refreshChangeIdText() {
		createChangeId = changeIdItem.getSelection();
		String text = commitText.getText().replaceAll(Text.DELIMITER, "\n"); //$NON-NLS-1$
		if (createChangeId) {
			String changedText = ChangeIdUtil.insertId(text,
					originalChangeId != null ? originalChangeId : ObjectId.zeroId(), true);
			if (!text.equals(changedText)) {
				changedText = changedText.replaceAll("\n", Text.DELIMITER); //$NON-NLS-1$
				commitText.setText(changedText);
			}
		} else {
			int changeIdOffset = findOffsetOfChangeIdLine(text);
			if (changeIdOffset > 0) {
				int endOfChangeId = findNextEOL(changeIdOffset, text);
				String cleanedText = text.substring(0, changeIdOffset)
						+ text.substring(endOfChangeId);
				cleanedText = cleanedText.replaceAll("\n", Text.DELIMITER); //$NON-NLS-1$
				commitText.setText(cleanedText);
			}
		}
	}

	private String getSignedOff() {
		return getSignedOff(committerText.getText());
	}

	private String getSignedOff(String signer) {
		return Constants.SIGNED_OFF_BY_TAG + signer;
	}

	private String signOff(String input) {
		String output = input;
		if (!output.endsWith(Text.DELIMITER))
			output += Text.DELIMITER;

			output += Text.DELIMITER;
		output += getSignedOff();
		return output;
