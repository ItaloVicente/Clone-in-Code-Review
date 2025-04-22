				try {
					if (project.exists()) {
						project.delete(true, null);
						TestUtil.waitForJobs(100, 5000);
					}
					IProjectDescription desc = ResourcesPlugin.getWorkspace()
							.newProjectDescription(projectName);
					desc.setLocation(new Path(
							new File(repository.getWorkTree(), projectName)
									.getPath()));
					project.create(desc, null);
					project.open(null);
					TestUtil.waitForJobs(50, 5000);
				} catch (InterruptedException e) {
					IStatus status = Activator
							.error("Creating Java project was interrupted", e);
					Thread.currentThread().interrupt();
					throw new CoreException(status);
