			cacheTreeMap.put(pathKey, factory.createFileModel(this,
					baseCommit, repoId, cacheId, fullPath));
	}

	@Override
	protected GitModelObject[] getChildrenImpl() {
		Collection<GitModelObject> values = cacheTreeMap.values();

		return values.toArray(new GitModelObject[values.size()]);
