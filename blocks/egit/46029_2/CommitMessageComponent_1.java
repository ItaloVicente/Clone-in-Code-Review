	private String formatIssuesInCommitMessage() {
		IDocument document = commitText != null ? //
				commitText.getDocument() : new Document(commitMessage);
		int numberOfLines = document.getNumberOfLines();
		if (numberOfLines > 1) {
			try {
				IRegion lineInfo = document.getLineInformation(1);
				if (lineInfo.getLength() > 0) {
					return UIText.CommitMessageComponent_MessageSecondLineNotEmpty;
				}
			} catch (BadLocationException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return null;
	}

