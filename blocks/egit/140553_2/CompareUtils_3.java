		for (IEditorReference editorRef : editorRefs) {
		    IEditorPart part = editorRef.getEditor(false);
		    if (part != null
			    && (part.getEditorInput() instanceof SaveableCompareEditorInput)
			    && part instanceof IReusableEditor && !part.isDirty()) {
			return part;
		    }
		}
