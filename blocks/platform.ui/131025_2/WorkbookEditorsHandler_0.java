	@Override
	protected String getWorkbenchPartReferenceText(WorkbenchPartReference ref) {
		StringBuilder str = new StringBuilder(super.getWorkbenchPartReferenceText(ref));
		if (ref instanceof EditorReference) {
			str.append(" - "); //$NON-NLS-1$
			str.append(getEditorInputPath((EditorReference) ref));
		}
		return str.toString();
	}

	private String getEditorInputPath(EditorReference editorReference) {
		try {
			IEditorInput input = editorReference.getEditorInput();
			return Adapters.adapt(input, IPathEditorInput.class).getPath().makeRelativeTo(Platform.getLocation())
					.toOSString();
		} catch (PartInitException e) {
			WorkbenchPlugin.log(getClass(), "getWorkbenchPartReferenceText", e); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

