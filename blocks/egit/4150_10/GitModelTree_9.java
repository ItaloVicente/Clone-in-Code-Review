	@Override
	public void dispose() {
		for (GitModelObject value : cachedTreeMap.values())
			value.dispose();

		cachedTreeMap.clear();
	}

