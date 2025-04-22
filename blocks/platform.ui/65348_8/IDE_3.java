	@Deprecated
	public static IEditorDescriptor getEditorDescriptor(String name, boolean inferContentType)
			throws PartInitException {
		return getEditorDescriptor(name, inferContentType, false);
	}

