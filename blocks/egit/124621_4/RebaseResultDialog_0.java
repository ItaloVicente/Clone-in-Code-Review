				IProject[] projects = ProjectUtil.getProjects(repo);
				try {
					ResourcesPlugin.getWorkspace().run(
							pm -> ProjectUtil.refreshResources(projects, pm),
							null, IWorkspace.AVOID_UPDATE, null);
				} catch (CoreException e) {
					Activator.logError(e.getMessage(), e);
