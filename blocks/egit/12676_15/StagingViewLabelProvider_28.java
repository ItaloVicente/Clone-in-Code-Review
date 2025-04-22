		return getDecoratedImage(getEditorImage(c), decoration.getOverlay());
	}

	@Override
	public String getText(Object element) {

		int presentation = stagingView.getPresentation();

		if (element instanceof StagingFolderEntry) {
			StagingFolderEntry stagingFolderEntry = (StagingFolderEntry) element;
			if (presentation == StagingView.PRESENTATION_COMPRESSED_FOLDERS) {
				if (stagingFolderEntry.getPath().toString().length() <= stagingView
						.getCurrentRepository().getWorkTree().getPath()
						.length() + 1) {
					return ""; //$NON-NLS-1$
				} else {
					return stagingFolderEntry
							.getPath()
							.toString()
							.substring(
									stagingView.getCurrentRepository()
											.getWorkTree().getPath().length() + 1);
				}
			} else {
				return stagingFolderEntry.getFile().getName();
			}
		}
