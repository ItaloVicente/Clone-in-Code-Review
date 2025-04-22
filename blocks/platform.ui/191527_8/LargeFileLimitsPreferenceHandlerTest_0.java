	private void createTestFile() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot workspaceRoot = workspace.getRoot();
		testProject = workspaceRoot.getProject("SomeProject");
		testProject.create(monitor);
		testProject.open(monitor);
		IPath path = new Path("/" + testProject.getName() + "/test_file" + "." + TXT_EXTENSION);
		temporaryFile = workspaceRoot.getFile(path);
		String content = String.join(System.lineSeparator(), "some line 1", "some line 2");
		boolean force = true;
		temporaryFile.create(new ByteArrayInputStream(content.getBytes()), force, monitor);
	}

