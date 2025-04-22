	private static String getChangedFilePath(
			Repository repository, GitCompareFileRevisionEditorInput gitCompareInput) {
		FileRevisionTypedElement leftRevision = gitCompareInput
				.getLeftRevision();
		IFile leftResource = (IFile) gitCompareInput.getAdapter(IFile.class);
		String changedFilePath = null;
		if (leftResource != null) {
			changedFilePath = repository.getWorkTree().toPath()
					.relativize(leftResource.getRawLocation().toFile().toPath())
					.toString();
		} else if (leftRevision != null) {
			changedFilePath = leftRevision.getPath();
		}
		if (changedFilePath == null) {
			FileRevisionTypedElement rightRevision = gitCompareInput
					.getRightRevision();
			if (rightRevision != null) {
				changedFilePath = rightRevision.getPath();
			}
		}
		return changedFilePath;
	}

