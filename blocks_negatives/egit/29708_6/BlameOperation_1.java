	private HistoryPageInput createHistoryPageInputWhenEditorOpened() {
		if (storage instanceof IFile) {
			IResource resource = (IResource) storage;
			if (startCommit != null) {
				return new BlameHistoryPageInput(repository, startCommit,
						resource);
			} else {
				return new HistoryPageInput(repository,
						new IResource[] { resource });
			}
		} else if (!repository.isBare()) {
			File file = new File(repository.getWorkTree(), path);
			if (startCommit != null) {
				return new BlameHistoryPageInput(repository, startCommit,
						file);
			} else {
				return new HistoryPageInput(repository,
						new File[] { file });
			}
		} else {
			return new HistoryPageInput(repository);
		}
	}

