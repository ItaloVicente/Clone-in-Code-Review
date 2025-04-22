		IProjectDescription description = ResourcesPlugin.getWorkspace()
				.newProjectDescription(projectName);

		description.setName(projectName);
		description.setLocationURI(locationURI);
		project.create(description, null);
