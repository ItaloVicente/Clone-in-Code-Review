			IEditorPart part = ref.getEditor(false);
			if (part != null) {
				IFile editorFile = getFile(part.getEditorInput());
				if (editorFile != null && file.equals(editorFile)) {
					return part;
				}
			}
		}
		return null;
	}

	public static IResource getResource(Object element) {
