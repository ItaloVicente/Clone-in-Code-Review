
			IProject project = commitItem.file.getProject();
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping == null) {
				return;
			}
			Repository repository = mapping.getRepository();

