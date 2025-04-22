	@Deprecated
	public static IEditorDescriptor getEditorDescriptor(String name, boolean inferContentType)
			throws PartInitException, OperationCanceledException {
		return getEditorDescriptor(name, inferContentType, false);
	}

