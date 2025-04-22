			GitProjectData gitData = GitProjectData.get((IProject) resource);
			if (gitData == null) {
				return false;
			}
			RepositoryMapping mapping = gitData.getRepositoryMapping(resource);
			if (mapping == null || !gitData.hasSubmodules()
					&& mapping.getRepository() != repository) {
				return false;
			}
