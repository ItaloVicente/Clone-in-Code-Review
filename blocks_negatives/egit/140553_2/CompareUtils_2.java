		for (int i = 0; i < editorRefs.length; i++) {
			IEditorPart part = editorRefs[i].getEditor(false);
			if (part != null
					&& (part.getEditorInput() instanceof GitCompareFileRevisionEditorInput || part.getEditorInput() instanceof GitCompareEditorInput)
					&& part instanceof IReusableEditor
					&& part.getEditorInput().equals(input)) {
				return part;
			}
