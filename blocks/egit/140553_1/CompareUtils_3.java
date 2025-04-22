	    for (IEditorReference editorRef : editorRefs) {
		IEditorPart part = editorRef.getEditor(false);
		if (part != null
			&& (part.getEditorInput() instanceof GitCompareFileRevisionEditorInput || part.getEditorInput() instanceof GitCompareEditorInput)
			&& part instanceof IReusableEditor
			&& part.getEditorInput().equals(input)) {
		    return part;
