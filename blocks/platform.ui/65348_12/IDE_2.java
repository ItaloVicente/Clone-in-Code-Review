	
	@Deprecated
	private static IEditorDescriptor getEditorDescriptor(String name,
			IEditorRegistry editorReg, IEditorDescriptor defaultDescriptor)
			throws PartInitException {

		if (defaultDescriptor != null) {
			return defaultDescriptor;
		}

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

		if (editorDesc == null) {
			throw new PartInitException(
					IDEWorkbenchMessages.IDE_noFileEditorFound);
		}

		return editorDesc;
	}
