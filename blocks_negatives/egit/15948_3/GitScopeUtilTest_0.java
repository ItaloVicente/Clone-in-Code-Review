		if (modelProject.exists())
			modelProject.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace()
				.newProjectDescription(PROJ1);
		modelProject.create(desc, null);
		modelProject.open(null);

		IFile modelFile = modelProject.getFile(MODEL_FILE);
