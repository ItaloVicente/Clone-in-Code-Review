			GitProjectData gitData = GitProjectData.get(resource.getProject());
			if (gitData == null) {
				return false;
			}
			RepositoryMapping mapping = gitData.getRepositoryMapping(resource);
			if (mapping == null || !gitData.isProtected(resource)
					&& mapping.getRepository() != repository) {
				return false;
			}
