		IWorkbenchPage page = window.getActivePage();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject testProject = workspace.getRoot().getProject(
				"MultiPageKeyBindingTest Project"); //$NON-NLS-1$
		testProject.create(null);
		testProject.open(null);
		IFile multiFile = testProject.getFile(fileName);
		multiFile.create(new ByteArrayInputStream(new byte[0]), true, null);
