		/*IProjectDescription projectDescription = testProject.getDescription();
		String[] natureIds = { "org.eclipse.jdt.core.javanature" };
		projectDescription.setNatureIds(natureIds);*/
		/*ICommand buildCommand = new BuildCommand();
		buildCommand.setBuilderName("org.eclipse.jdt.core.javabuilder");
		projectDescription.setBuildSpec(new ICommand[] { buildCommand });
		testProject.setDescription(projectDescription, null);*/

		for (String EDITOR_FILE_EXTENSION : EditorPerformanceSuite.EDITOR_FILE_EXTENSIONS) {
