		IEditorDescriptor editorDesc = defaultDescriptor;

		if (editorReg.isSystemInPlaceEditorAvailable(name)) {
			editorDesc = editorReg
					.findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
		}

		if (editorDesc == null
				&& editorReg.isSystemExternalEditorAvailable(name)) {
			editorDesc = editorReg
					.findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
		}

		if (editorDesc == null) {
			editorDesc = editorReg
					.findEditor(IDEWorkbenchPlugin.DEFAULT_TEXT_EDITOR_ID);
		}
