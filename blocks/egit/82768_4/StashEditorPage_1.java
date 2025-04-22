	@Override
	protected Collection<IGlobalActionProvider> getGlobalActionProviders() {
		Set<IGlobalActionProvider> result = new HashSet<>(
				super.getGlobalActionProviders());
		result.add(stagedDiffViewer);
		return result;
	}

