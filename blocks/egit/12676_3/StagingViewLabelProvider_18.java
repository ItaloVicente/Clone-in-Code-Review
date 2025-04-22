	@Override
	public String getText(Object element) {

		int presentation;
		if (unstagedSection) {
			presentation = stagingView.getUnstagedPresentation();
		} else {
			presentation = stagingView.getStagedPresentation();
		}

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

		StagingEntry stagingEntry = (StagingEntry) element;
		final DecorationResult decoration = new DecorationResult();
		decorationHelper.decorate(decoration, stagingEntry);
