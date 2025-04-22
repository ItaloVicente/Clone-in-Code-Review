	private IEditorPart openNonExternalEditor(IWorkbenchPage page, IFile file) {
		IEditorPart result;
		try {
			IEditorDescriptor defaultEditorDesc = IDE.getDefaultEditor(file);
			if (defaultEditorDesc != null
					&& !defaultEditorDesc.isOpenExternal()) {
				result = IDE.openEditor(page, file, true);
			} else {
				IEditorRegistry editorReg = PlatformUI.getWorkbench()
						.getEditorRegistry();
				IEditorDescriptor editorDesc = null;
				if (editorReg.isSystemInPlaceEditorAvailable(file.getName())) {
					editorDesc = editorReg
							.findEditor(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
				}
