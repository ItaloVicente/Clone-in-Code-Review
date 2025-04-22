	@Deprecated
	public static IEditorDescriptor getEditorDescriptor(IFile file) throws PartInitException {
		try {
			return getEditorDescriptor(file, true, false);
		} catch (OperationCanceledException ex) {
			throw new PartInitException(ex.getMessage(), ex);
		}
