		super(parent, commit, null, repoId, cacheId, repoId, location);
	}

	@Override
	protected GitCompareInput getCompareInput(ComparisonDataSource baseData,
			ComparisonDataSource remoteData, ComparisonDataSource ancestorData) {
		return new GitCacheCompareInput(getRepository(), ancestorData,
				baseData, remoteData, gitPath);
