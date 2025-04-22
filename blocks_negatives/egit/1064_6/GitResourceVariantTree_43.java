
		Repository repository = gsdData.getData(resource.getProject())
				.getRepository();

		if (resource instanceof IProject) {
			return new GitFolderResourceVariant(resource);
		} else if (resource instanceof IFolder) {
			return findFolderVariant(resource, repository);
		}

		return findFileVariant(resource, repository);
