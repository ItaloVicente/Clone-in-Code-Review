			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(resource.getProject());
			repository = repositoryMapping.getRepository();
		} else if (adaptableObject instanceof PlatformObject) {
			PlatformObject platformObject = (PlatformObject) adaptableObject;
			repository = Utils.getAdapter(platformObject, Repository.class);
