	private Repository getRepository(IResource local) {
		if (!ResourceUtil.isNonWorkspace(local))
			return gsds.getData(local.getProject()).getRepository();

		return RepositoryMapping.getMapping(local.getFullPath()).getRepository();
	}
