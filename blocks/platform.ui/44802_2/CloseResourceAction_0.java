	private static IResource getAdapter(IEditorReference ref) {
		IEditorInput input;
		try {
			input = ref.getEditorInput();
		} catch (PartInitException e) {
			return null;
		}
