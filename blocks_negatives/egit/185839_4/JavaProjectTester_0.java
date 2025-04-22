				IProjectDescription desc = ResourcesPlugin.getWorkspace()
						.newProjectDescription(projectName);
				desc.setLocation(
						new Path(new File(repository.getWorkTree(), projectName)
								.getPath()));
				project.create(desc, null);
				project.open(null);
				TestUtil.waitForJobs(50, 5000);
