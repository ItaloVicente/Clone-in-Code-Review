	@Deprecated
	public static IEditorDescriptor getEditorDescriptor(String name, boolean inferContentType)
			throws PartInitException, OperationCanceledException {
		return getEditorDescriptor(name, inferContentType, false);
	}

	public static IEditorDescriptor getEditorDescriptor(String name, boolean inferContentType, boolean allowInteractive)
			throws PartInitException, OperationCanceledException {
