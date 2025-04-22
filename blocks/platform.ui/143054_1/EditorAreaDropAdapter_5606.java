	private IEditorPart openNonExternalEditor(IWorkbenchPage page,
			IEditorInput editorInput, String editorId) {
		IEditorPart result;
		try {
			IEditorRegistry editorReg = PlatformUI.getWorkbench()
					.getEditorRegistry();
			IEditorDescriptor editorDesc = editorReg.findEditor(editorId);
			if (editorDesc != null && !editorDesc.isOpenExternal()) {
				result = page.openEditor(editorInput, editorId);
			} else {
				result = null;
			}
		} catch (PartInitException e) {
			result = null;
		}
		return result;
	}
