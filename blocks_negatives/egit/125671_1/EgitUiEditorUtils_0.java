	private static String getEditorId(FileRevisionEditorInput editorInput) {
		String id = getEditorId(editorInput.getFileRevision().getName(),
				getContentType(editorInput));
		return id;
	}

	private static String getEditorId(String fileName, IContentType type) {
