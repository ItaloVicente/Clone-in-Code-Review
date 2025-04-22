	@Override
	protected Collection<IGlobalActionProvider> getGlobalActionProviders() {
		Set<IGlobalActionProvider> result = new HashSet<>(
				super.getGlobalActionProviders());
		result.add(stagedDiffViewer);
		return result;
	}

	@Override
	public void dispose() {
		if (globalActionHandler != null) {
			globalActionHandler.dispose();
		}
	}

