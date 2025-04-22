	private IEditorPart openNonExternalEditor(IWorkbenchPage page,
			IMarker marker) {
		IEditorPart result;
		try {
			if (!(marker.getResource() instanceof IFile)) {
				return null;
			}
			IFile file = (IFile) marker.getResource();
