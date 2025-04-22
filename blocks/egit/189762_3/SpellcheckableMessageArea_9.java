	public void setCleanupMode(@NonNull CleanupMode mode, char commentChar) {
		if (CleanupMode.DEFAULT.equals(mode)) {
			throw new IllegalArgumentException(
					"Clean-up mode must not be " + mode); //$NON-NLS-1$
		}
		this.cleanupMode = mode;
		this.commentChar = commentChar;
	}

	public void invalidatePresentation() {
		sourceViewer.invalidateTextPresentation();
		IDocument document = getDocument();
		TextUtilities.addDocumentPartitioners(document,
				TextUtilities.removeDocumentPartitioners(document));
	}

