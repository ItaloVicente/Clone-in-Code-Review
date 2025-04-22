		IEditorDescriptor editorDesc;
		try {
			editorDesc = strategy.getEditorDescriptor(name, editorReg);
		} catch (CoreException e) {
			throw new PartInitException(IDEWorkbenchMessages.IDE_noFileEditorFound, e);
		}
