	public static IEditorDescriptor getEditorDescriptor(String name, boolean inferContentType, boolean allowInteractive)
			throws PartInitException, OperationCanceledException {

		if (name == null) {
			throw new IllegalArgumentException();
		}

		IContentType contentType = inferContentType ? Platform
				.getContentTypeManager().findContentTypeFor(name) : null;
		IEditorRegistry editorReg = PlatformUI.getWorkbench()
				.getEditorRegistry();

		IEditorDescriptor defaultEditor = editorReg.getDefaultEditor(name, contentType);
		defaultEditor = getEditorDescriptor(name, editorReg, defaultEditor, allowInteractive);
		return overrideDefaultEditorAssociation(name, contentType, defaultEditor);
	}

