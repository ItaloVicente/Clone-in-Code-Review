	protected MockEditorPart openEditor(IWorkbenchPage page, String suffix)
			throws Throwable {
		IProject proj = FileUtil.createProject("IEditorActionDelegateTest");
		IFile file = FileUtil.createFile("test" + suffix + ".txt", proj);
		return (MockEditorPart) page.openEditor(new FileEditorInput(file),
				EDITOR_ID);
	}
