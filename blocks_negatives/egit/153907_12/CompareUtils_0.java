	private static void openCompareToolExternal(Repository repository,
			CompareEditorInput input) {
		GitCompareFileRevisionEditorInput gitCompareInput = (GitCompareFileRevisionEditorInput) input;
		FileRevisionTypedElement leftRevision = gitCompareInput
				.getLeftRevision();
		IFile leftResource = gitCompareInput.getAdapter(IFile.class);
		FileRevisionTypedElement rightRevision = gitCompareInput
				.getRightRevision();
		String mergedFilePath = null;
		if (leftResource != null) {
			mergedFilePath = leftResource.getName();
		} else if (leftRevision != null) {
			mergedFilePath = leftRevision.getPath();
		}
