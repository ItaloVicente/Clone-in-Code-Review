	public void testDoubleDelete() throws CommandException,
			CoreException, IOException {
		IWorkbenchWindow window = openTestWindow();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject testProject = workspace.getRoot().getProject(
				"DoubleDeleteestProject"); //$NON-NLS-1$
		testProject.create(null);
		testProject.open(null);
		IFile textFile = testProject.getFile("A.txt"); //$NON-NLS-1$
		String originalContents = "A blurb"; //$NON-NLS-1$
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				originalContents.getBytes());
		textFile.create(inputStream, true, null);
		IDE.openEditor(window.getActivePage(), textFile,
				true);
