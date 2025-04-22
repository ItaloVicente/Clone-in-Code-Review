		IEditorDescriptor editorDesc = null;
		editorDesc = editorReg.findEditor(IDEWorkbenchPlugin.DEFAULT_TEXT_EDITOR_ID);

		status = (editorDesc != null) ? IStatus.OK : IStatus.ERROR;

		return editorDesc;
