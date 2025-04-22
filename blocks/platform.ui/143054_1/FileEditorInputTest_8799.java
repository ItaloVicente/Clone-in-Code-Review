		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IPath path = new Path("/foo/bar.txt");
		IFile fileA = workspace.getRoot().getFile(path);
		FileEditorInput inputA1 = new FileEditorInput(fileA);
		OtherFileEditorInput inputA2 = new OtherFileEditorInput(fileA);
		assertTrue(inputA1.equals(inputA2));
		assertTrue(inputA2.equals(inputA1));
	}

	class OtherFileEditorInput implements IFileEditorInput {
		private IFile file;

		public OtherFileEditorInput(IFile file) {
			this.file = file;
		}

		@Override
