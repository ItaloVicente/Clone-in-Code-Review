		if (gsd != null)
			repo = gsd.getRepository();
		else {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource.getFullPath());
			if (mapping != null) {
				repo = mapping.getRepository();
				gsd = gsds.getData(repo);
			}
		}
		if (gsd == null || repo == null)
