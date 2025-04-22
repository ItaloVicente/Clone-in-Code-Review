		cacheTreeMap = new HashMap<String, GitModelObject>();
	}

	@Override
	public GitModelObject[] getChildren() {
		Collection<GitModelObject> values = cacheTreeMap.values();

		return values.toArray(new GitModelObject[values.size()]);
