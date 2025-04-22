		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		testProject = workspace.getRoot().getProject(PROJECT_NAME);
		testProject.create(null);
		testProject.open(null);
		String[] natureIds = { "org.eclipse.jdt.core.javanature" };
		projectDescription.setNatureIds(natureIds);*/
		buildCommand.setBuilderName("org.eclipse.jdt.core.javabuilder");
		projectDescription.setBuildSpec(new ICommand[] { buildCommand });
		testProject.setDescription(projectDescription, null);*/

		for (String EDITOR_FILE_EXTENSION : EditorPerformanceSuite.EDITOR_FILE_EXTENSIONS) {
			createFiles(EDITOR_FILE_EXTENSION);
		}
