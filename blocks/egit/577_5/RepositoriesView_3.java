					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(dir.getParentFile().getName());
					Repository repo;
					if (project.exists()) {
						RepositoryMapping repoMapping = RepositoryMapping.getMapping(project);
						repo = repoMapping.getRepository();
					} else {
						repo = new Repository(dir);
					}
