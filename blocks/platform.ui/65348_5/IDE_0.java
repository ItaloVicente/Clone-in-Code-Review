	@Deprecated
	public static IEditorDescriptor getEditorDescriptor(IFile file, boolean determineContentType)
			throws PartInitException, OperationCanceledException {

		if (file == null) {
			throw new IllegalArgumentException();
		}

		return getEditorDescriptor(file.getName(), PlatformUI.getWorkbench().getEditorRegistry(),
				getDefaultEditor(file, determineContentType), false);
	}

	public static IEditorDescriptor getEditorDescriptor(IFile file, boolean determineContentType, boolean allowInteractive)
			throws PartInitException, OperationCanceledException {
